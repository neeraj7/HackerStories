package com.questionpro.hackerstories.handler;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.questionpro.hackerstories.constants.Constants;
import com.questionpro.hackerstories.model.HackerError;
import com.questionpro.hackerstories.service.HackerStoryService;
import reactor.core.publisher.Mono;

/**
 * The HackerHandler class.
 * 
 * Acts as a mediator between request and business logic.
 * 
 * @author neeraj.kumar
 *
 */
@Component
public class HackerHandler {

  /**
   * Hacker story service instance.
   */
  @Autowired
  private HackerStoryService service;

  /**
   * Get the best stories.
   * 
   * @param request
   * @return Mono<ServerResponse>
   */
  public Mono<ServerResponse> getBestStories(ServerRequest request) {
    return service.getBestStories()
        .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromValue(res.getBestStories())))
        .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(BodyInserters.fromValue(
                prepareErrorResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage()))));
  }

  /**
   * Get the past best stories.
   * 
   * @param request
   * @return Mono<ServerResponse>
   */
  public Mono<ServerResponse> getPastStories(ServerRequest request) {
    return service.getPastStories()
        .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromValue(res.getBestStories())))
        .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(BodyInserters.fromValue(
                prepareErrorResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage()))));
  }

  /**
   * Get the top comments of given story.
   * 
   * @param request
   * @return Mono<ServerResponse>
   */
  public Mono<ServerResponse> getTopCommentsOfGivenStory(ServerRequest request) {
    Optional<String> storyId = request.queryParam(Constants.STORY);
    if (storyId.isPresent() && !storyId.get().isEmpty()) {
      return service.getTopCommentsOfGivenStory(storyId.get())
          .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromValue(res.getTopComments())))
          .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(BodyInserters.fromValue(
                  prepareErrorResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage()))));
    } else {
      return ServerResponse.badRequest().body(BodyInserters
          .fromValue(prepareErrorResponseBody(HttpStatus.BAD_REQUEST, "Story id is missing.")));
    }
  }

  private HackerError prepareErrorResponseBody(HttpStatus status, String errorMsg) {
    HackerError error = new HackerError();
    error.setErrorCode(status.name());
    error.setErrorStatus(status.value());
    error.setErrorMsg(errorMsg);
    return error;
  }
}
