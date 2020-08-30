package com.questionpro.hackerstories.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.questionpro.hackerstories.handler.HackerHandler;

@Configuration
public class HackerRoutes {

	@Bean
	public RouterFunction<ServerResponse> route(HackerHandler handler) {
		return RouterFunctions.route(
				RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
				handler::hello)
				.andRoute(RequestPredicates.GET("/best-stories").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
				handler::getBestStories);
	}

}
