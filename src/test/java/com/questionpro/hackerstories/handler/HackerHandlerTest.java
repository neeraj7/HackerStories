package com.questionpro.hackerstories.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.questionpro.hackerstories.constants.Constants;
import com.questionpro.hackerstories.model.CommentModel;
import com.questionpro.hackerstories.model.CommentResponse;
import com.questionpro.hackerstories.model.StoryModel;
import com.questionpro.hackerstories.model.StoryResponse;
import com.questionpro.hackerstories.service.HackerStoryService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The HackerHandlerTest class.
 * 
 * @author neeraj.kumar
 *
 */
public class HackerHandlerTest {

  /**
   * Instance of class under test.
   */
  @InjectMocks
  private HackerHandler handler;

  /**
   * HackerStoryService mock instance.
   */
  @Mock
  private HackerStoryService service;

  /**
   * ServerRequest mock.
   */
  @Mock
  private ServerRequest request;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Test the get best stories handler function.
   */
  @Test
  void testGetBestStories() {
    StoryResponse storyResponse = new StoryResponse();
    StoryModel storyModel = new StoryModel();
    List<StoryModel> stories = new ArrayList<>();
    stories.add(storyModel);
    storyResponse.setBestStories(stories);
    Mockito.when(service.getBestStories()).thenReturn(Mono.just(storyResponse));
    Mono<ServerResponse> response = handler.getBestStories(request);
    StepVerifier.create(response).expectNextMatches(res -> res.statusCode().equals(HttpStatus.OK))
        .verifyComplete();
  }

  /**
   * Test the get past stories handler function.
   */
  @Test
  void testPastStories() {
    StoryResponse storyResponse = new StoryResponse();
    StoryModel storyModel = new StoryModel();
    List<StoryModel> stories = new ArrayList<>();
    stories.add(storyModel);
    storyResponse.setBestStories(stories);
    Mockito.when(service.getPastStories()).thenReturn(Mono.just(storyResponse));
    Mono<ServerResponse> response = handler.getPastStories(request);
    StepVerifier.create(response).expectNextMatches(res -> res.statusCode().equals(HttpStatus.OK))
        .verifyComplete();
  }

  /**
   * Test the top comments of given story handler function.
   */
  @Test
  void testGetTopCommentsOfGivenStory() {
    CommentResponse commentResponse = new CommentResponse();
    CommentModel commentModel = new CommentModel();
    List<CommentModel> comments = new ArrayList<>();
    comments.add(commentModel);
    commentResponse.setTopComments(comments);
    Mockito.when(service.getTopCommentsOfGivenStory(Mockito.anyString()))
        .thenReturn(Mono.just(commentResponse));
    Mockito.when(request.queryParam(Constants.STORY)).thenReturn(Optional.of("storyId"));
    Mono<ServerResponse> response = handler.getTopCommentsOfGivenStory(request);
    StepVerifier.create(response).expectNextMatches(res -> res.statusCode().equals(HttpStatus.OK))
        .verifyComplete();
  }
  
  /**
   * Test the top comments of given story handler function.
   * This time, when story query param is not in request.
   */
  @Test
  void testGetTopCommentsOfGivenStoryWithoutStoryId() {
    CommentResponse commentResponse = new CommentResponse();
    CommentModel commentModel = new CommentModel();
    List<CommentModel> comments = new ArrayList<>();
    comments.add(commentModel);
    commentResponse.setTopComments(comments);
    Mockito.when(service.getTopCommentsOfGivenStory(Mockito.anyString()))
        .thenReturn(Mono.just(commentResponse));
    Mono<ServerResponse> response = handler.getTopCommentsOfGivenStory(request);
    StepVerifier.create(response).expectNextMatches(res -> res.statusCode().equals(HttpStatus.BAD_REQUEST))
        .verifyComplete();
  }
  
  /**
   * Test the top comments of given story handler function.
   * This time, story id is missing from the request.
   */
  @Test
  void testGetTopCommentsOfGivenStoryWithMissingStoryId() {
    CommentResponse commentResponse = new CommentResponse();
    CommentModel commentModel = new CommentModel();
    List<CommentModel> comments = new ArrayList<>();
    comments.add(commentModel);
    commentResponse.setTopComments(comments);
    Mockito.when(service.getTopCommentsOfGivenStory(Mockito.anyString()))
        .thenReturn(Mono.just(commentResponse));
    Mockito.when(request.queryParam(Constants.STORY)).thenReturn(Optional.of(""));
    Mono<ServerResponse> response = handler.getTopCommentsOfGivenStory(request);
    StepVerifier.create(response).expectNextMatches(res -> res.statusCode().equals(HttpStatus.BAD_REQUEST))
        .verifyComplete();
  }

}
