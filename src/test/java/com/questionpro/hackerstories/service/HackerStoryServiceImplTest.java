package com.questionpro.hackerstories.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import com.questionpro.hackerstories.helper.WebClientHelper;
import com.questionpro.hackerstories.model.CommentResponse;
import com.questionpro.hackerstories.model.StoryModel;
import com.questionpro.hackerstories.model.StoryResponse;
import com.questionpro.hackerstories.model.api.Comment;
import com.questionpro.hackerstories.model.api.Story;
import com.questionpro.hackerstories.repository.StoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * A JUnit test class to test HackerStoryServiceImpl class.
 * 
 * @author neeraj.kumar
 *
 */
public class HackerStoryServiceImplTest {
  
  /**
   * Instance of class under test.
   */
  @InjectMocks
  private HackerStoryServiceImpl service;
  
  /**
   * Scheduler to schedule runnable tasks.
   */
  @Mock
  private ThreadPoolTaskScheduler taskScheduler;

  /**
   * StoryRepository instance to handle DB calls.
   */
  @Mock
  private StoryRepository repo;
  
  /**
   * WebClientHelper instance.
   */
  @Mock
  private WebClientHelper webClientHelper;
  
  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  void testGetBestStories() {
    ClientResponse cr = Mockito.mock(ClientResponse.class);
    Integer[] arr = {1,2,3};
    StoryModel storyModel = new StoryModel();
    Mockito.when(repo.saveAll(Mockito.anyIterable())).thenReturn(Flux.just(storyModel));
    
    ReflectionTestUtils.setField(service, "bestStoriesLimit", 1);
    Story story = new Story();
    Mockito.when(cr.bodyToMono(Story.class)).thenReturn(Mono.just(story));
    
    Mockito.when(cr.bodyToMono(Integer[].class)).thenReturn(Mono.just(arr));
    Mockito.when(webClientHelper.getCall(Mockito.anyString())).thenReturn(Mono.just(cr));
    Mono<StoryResponse> response = service.getBestStories();
    StepVerifier.create(response).expectNextMatches(r -> {
      return null != r;
    }).verifyComplete();
  }
  
  @Test
  void testPastStories() {
    StoryModel storyModel = new StoryModel();
    Mockito.when(repo.findAll()).thenReturn(Flux.just(storyModel));
    Mono<StoryResponse> response = service.getBestStories();
    StepVerifier.create(response).expectNextMatches(r -> {
      return null != r;
    }).verifyComplete();
  }
  
  @Test
  void testGetTopCommentsOfGivenStory() {
    ClientResponse cr = Mockito.mock(ClientResponse.class);
    Story story = new Story();
    List<Integer> kids = new ArrayList<>();
    kids.add(1);
    story.setKids(kids);
    Mockito.when(webClientHelper.getCall(Mockito.anyString())).thenReturn(Mono.just(cr));
    Mockito.when(cr.bodyToMono(Story.class)).thenReturn(Mono.just(story));
    Comment comment = new Comment();
    Mockito.when(cr.bodyToMono(Comment.class)).thenReturn(Mono.just(comment));
    
    Mono<CommentResponse> response = service.getTopCommentsOfGivenStory("storyId");
    StepVerifier.create(response).expectNextMatches(r -> {
      return null != r;
    }).verifyComplete();
  }

}
