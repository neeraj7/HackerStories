package com.questionpro.hackerstories.handler;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.questionpro.hackerstories.constants.Constants;
import com.questionpro.hackerstories.service.HackerStoryService;
import reactor.core.publisher.Mono;

/**
 * The HackerHandler class.
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

  public Mono<ServerResponse> hello(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue("{ \"value\":\"Hello, great start.\"}"));
  }

  /**
   * Get the best stories.
   * 
   * @param request
   * @return Mono<ServerResponse>
   */
  public Mono<ServerResponse> getBestStories(ServerRequest request) {
    return service.getBestStories()
        .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromValue(res)));
  }

  /**
   * Get the past best stories.
   * 
   * @param request
   * @return Mono<ServerResponse>
   */
  public Mono<ServerResponse> getPastStories(ServerRequest request) {
    return service.getPastStories()
        .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromValue(res)));
  }

  /**
   * Get the top comments of given story.
   * 
   * @param request
   * @return Mono<ServerResponse>
   */
  public Mono<ServerResponse> getTopCommentsOfGivenStory(ServerRequest request) {
    Optional<String> storyId = request.queryParam(Constants.STORY);
    if (storyId.isPresent()) {
      return service.getTopCommentsOfGivenStory(storyId.get())
          .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromValue(res)));
    } else {
      return ServerResponse.badRequest().body(BodyInserters.fromValue("Please input the story id"));
    }
  }
}