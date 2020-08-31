package com.questionpro.hackerstories.service;

import org.springframework.stereotype.Component;
import com.questionpro.hackerstories.model.CommentResponse;
import com.questionpro.hackerstories.model.StoryResponse;
import reactor.core.publisher.Mono;

/**
 * The HackerStoryService interface.
 * 
 * @author neeraj.kumar
 *
 */
@Component
public interface HackerStoryService {

  /**
   * Get best stories.
   * 
   * @return Mono<StoryResponse>
   */
  Mono<StoryResponse> getBestStories();

  /**
   * Get past stories.
   * 
   * @return Mono<StoryResponse>
   */
  Mono<StoryResponse> getPastStories();

  /**
   * Get given story's top comments.
   * 
   * @param storyId
   * @return Mono<CommentResponse>
   */
  Mono<CommentResponse> getTopCommentsOfGivenStory(String storyId);

}
