package com.questionpro.hackerstories.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.questionpro.hackerstories.constants.Constants;
import com.questionpro.hackerstories.handler.HackerHandler;

/**
 * The HackerRoutes class.
 * Holds the mapping of supported http requests with respective handlers.
 * 
 * @author neeraj.kumar
 *
 */
@Configuration
public class HackerRoutes {

  /**
   * Mapping the http request with the appropriate handler functions.
   * @param handler
   * @return RouterFunction<ServerResponse>
   */
  @Bean
  public RouterFunction<ServerResponse> route(HackerHandler handler) {
    return RouterFunctions
        .route(RequestPredicates.GET(Constants.BEST_HYPHEN_STORIES)
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getBestStories)
        .andRoute(RequestPredicates.GET(Constants.PAST_HYPHEN_STORIES)
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getPastStories)
        .andRoute(RequestPredicates.GET(Constants.COMMENTS), handler::getTopCommentsOfGivenStory);
  }
}