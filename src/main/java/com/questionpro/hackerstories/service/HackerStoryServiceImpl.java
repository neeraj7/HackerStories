package com.questionpro.hackerstories.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import com.questionpro.hackerstories.constants.Constants;
import com.questionpro.hackerstories.helper.WebClientHelper;
import com.questionpro.hackerstories.model.CommentModel;
import com.questionpro.hackerstories.model.CommentResponse;
import com.questionpro.hackerstories.model.StoryModel;
import com.questionpro.hackerstories.model.StoryResponse;
import com.questionpro.hackerstories.model.api.Comment;
import com.questionpro.hackerstories.model.api.Story;
import com.questionpro.hackerstories.model.api.User;
import com.questionpro.hackerstories.repository.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The HackerStoryServiceImpl class.
 * 
 * @author neeraj.kumar
 *
 */
@Component
@Slf4j
public class HackerStoryServiceImpl implements HackerStoryService {

  /**
   * Map<k,v> used as in-memory cache.
   */
  private Map<String, StoryResponse> cache = new ConcurrentHashMap<>();

  /**
   * Scheduler to schedule runnable tasks.
   */
  @Autowired
  private ThreadPoolTaskScheduler taskScheduler;

  /**
   * StoryRepository instance to handle DB calls.
   */
  @Autowired
  private StoryRepository repo;
  
  /**
   * WebClientHelper instance.
   */
  @Autowired
  private WebClientHelper webClientHelper;

  /**
   * Expiration time of values in cache.
   */
  @Value("${config.cache.ttl.in.minutes:15}")
  private int cacheTTL;
  
  /**
   * Best stories limit for response.
   */
  @Value("${config.best.stories.limit:10}")
  private int bestStoriesLimit;
  
  /**
   * Top comments limit on a given story for response.
   */
  @Value("${config.top.story.comments.limit:10}")
  private int topStoryCommentsLimit;

  /**
   * Get best stories.
   * 
   * @return if cache is not empty, return the cache value else make a call to hacker news api to
   *         fetch stories.
   */
  @Override
  public Mono<StoryResponse> getBestStories() {
    log.debug("Entered into getBestStories");
    return Mono.justOrEmpty(cache.get(Constants.BEST_STORIES))
        .switchIfEmpty(webClientHelper.getCall(Constants.BEST_STORIES_URL)
            .flatMap(res -> res.bodyToMono(Integer[].class))
            .flatMap(res -> getStoriesFromApi(res)));
  }

  /**
   * Get details of the stories from the hackernews api.
   * 
   * @param items
   * @return Mono<StoryResponse>
   */
  private Mono<StoryResponse> getStoriesFromApi(Integer[] items) {
    log.debug("Entered into getStoriesFromApi");
    return Flux.fromArray(items).take(this.bestStoriesLimit)
        .flatMap(item -> getItemsFromApi(item.toString())
            .flatMap(res -> res.bodyToMono(Story.class)))
        .map(story -> prepareStoryModel(story))
        .collect(Collectors.toList())
        .map(list -> prepareStoryResponse(list))
        .flatMap(sr -> saveIntoDB(sr));
  }

  /**
   * Get item from the hackernews api.
   * 
   * @param item
   * @param clazz
   * @return Mono<ClientResponse>
   */
  private Mono<ClientResponse> getItemsFromApi(String item) {
    log.debug("Entered into getItemsFromApi with item id {}", item);
    return webClientHelper.getCall(Constants.ITEM_URL.concat(item + Constants.DOT_JSON));
  }

  /**
   * Save the stories into the db.
   * 
   * @param storyResponse
   * @return Mono<StoryResponse>
   */
  private Mono<StoryResponse> saveIntoDB(StoryResponse storyResponse) {
    log.debug("Entered into saveIntoDB with StoryResponse {}", storyResponse.getBestStories());
    return repo.saveAll(storyResponse.getBestStories())
        .then(Mono.just(storyResponse))
        .flatMap(r -> saveIntoCache(r));
  }

  /**
   * Save the stories into cache.
   * 
   * @param storyResponse
   * @return Mono<StoryResponse>
   */
  private Mono<StoryResponse> saveIntoCache(StoryResponse storyResponse) {
    log.debug("Entered into saveIntoCache with StoryResponse {}", storyResponse);
    cache.put(Constants.BEST_STORIES, storyResponse);
    log.debug("Added data into cache with key {} and TTL {} minutes", Constants.BEST_STORIES, cacheTTL);
    Runnable removeBestStories = () -> {
      log.debug("Removed best stories from in-memory cache.");
      cache.remove(Constants.BEST_STORIES);
    };
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MINUTE, cacheTTL);
    taskScheduler.schedule(removeBestStories, cal.getTime());
    log.debug("Task scheduled to remove the best stories from in-memory cache at time {}", cal.getTime());
    return Mono.just(storyResponse);
  }

  /**
   * Prepare the story response with list of story models.
   * 
   * @param list
   * @return StoryResponse
   */
  private StoryResponse prepareStoryResponse(List<StoryModel> list) {
    log.debug("Entered into prepareStoryResponse");
    StoryResponse storyResponse = new StoryResponse();
    storyResponse.setBestStories(list);
    return storyResponse;
  }

  /**
   * Prepare story model from Story recieved from api.
   * 
   * @param story
   * @return StoryModel
   */
  private StoryModel prepareStoryModel(Story story) {
    log.debug("Entered into prepareStoryModel with Story {}", story);
    StoryModel storyModel = new StoryModel();
    storyModel.setId(story.getId());
    storyModel.setScore(story.getScore());
    storyModel.setTime(story.getTime());
    storyModel.setUrl(story.getUrl());
    storyModel.setTitle(story.getTitle());
    storyModel.setUser(story.getBy());
    return storyModel;
  }

  /**
   * Get all the best stories stored in the db.
   * 
   * @return Mono<StoryResponse>
   */
  @Override
  public Mono<StoryResponse> getPastStories() {
    log.debug("Entered into getPastStories");
    return repo.findAll()
        .collect(Collectors.toList())
        .map(list -> prepareStoryResponse(list));
  }

  /**
   * Get the top comments of given story.
   * 
   * @return Mono<StoryResponse>
   */
  @Override
  public Mono<CommentResponse> getTopCommentsOfGivenStory(String storyId) {
    log.debug("Entered into getTopCommentsOfGivenStory with story id {}", storyId);
    return getItemsFromApi(storyId).flatMap(res -> res.bodyToMono(Story.class))
        .flatMapMany(r -> Flux.fromIterable(r.getKids()))
        .flatMap(commentId -> {
          return getItemsFromApi(commentId.toString())
              .flatMap(r -> r.bodyToMono(Comment.class));
        }).collect(Collectors.toList())
        .flatMapMany(list -> {
          list.sort(new SortByChildComments());
          return Flux.fromIterable(list);
        }).take(this.topStoryCommentsLimit)
        .flatMap(comment -> prepareCommentModel(comment))
        .collect(Collectors.toList())
        .flatMap(list -> prepareCommentResponse(list));
  }
  
  /**
   * Prepare comment response from list of comment models.
   * 
   * @param comments
   * @return Mono<CommentResponse>
   */
  private Mono<CommentResponse> prepareCommentResponse(List<CommentModel> comments) {
    log.debug("Entered into prepareCommentResponse");
    CommentResponse commentResponse = new CommentResponse();
    commentResponse.setTopComments(comments);
    return Mono.just(commentResponse);
  }

  /**
   * Prepare comment model from comment.
   * 
   * @param comment
   * @return Mono<CommentModel>
   */
  private Mono<CommentModel> prepareCommentModel(Comment comment) {
    log.debug("Entered into prepareCommentModel with Comment {}", comment);
    if (!ObjectUtils.isEmpty(comment.getBy())) {
     return  webClientHelper.getCall(Constants.USER_URL.concat(comment.getBy()).concat(Constants.DOT_JSON))
      .flatMap(res -> res.bodyToMono(User.class))
      .flatMap(user -> {
        CommentModel commentModel = new CommentModel();
        commentModel.setUser(user.getId());
        LocalDate startDate = LocalDate.ofInstant(Instant.ofEpochSecond(user.getCreated()), ZoneId.systemDefault());
        commentModel.setUserProfileAge(Period.between(startDate, LocalDate.now()).getYears());
        commentModel.setText(comment.getText());
        return Mono.just(commentModel);
      });
    }
    
    CommentModel commentModel = new CommentModel();
    commentModel.setText(comment.getText());
    return Mono.just(commentModel);
  }
}

/**
 * Comparator class to compare the Comment based on total number of child comments.
 * 
 * @author neeraj.kumar
 *
 */
class SortByChildComments implements Comparator<Comment> {
  @Override
  public int compare(Comment o1, Comment o2) {
    if (ObjectUtils.isEmpty(o1.getKids())) {
      if (ObjectUtils.isEmpty(o2.getKids())) {
        return 0;
      }
      return 1;
    } else if (ObjectUtils.isEmpty(o2.getKids())) {
      return -1;
    }
    return o2.getKids().size() - o1.getKids().size();
  }
}
