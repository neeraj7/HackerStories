package com.questionpro.hackerstories.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.questionpro.hackerstories.service.HackerStoryService;

import reactor.core.publisher.Mono;

@Component
public class HackerHandler {
	
	@Autowired
	private HackerStoryService service;

	public Mono<ServerResponse> hello(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue("{ \"value\":\"Hello, great start.\"}"));
	}

	public Mono<ServerResponse> getBestStories(ServerRequest request) {
		return service.getBestStories()
				.map(f -> {
					System.out.println("first title: " + f.getBestStories().get(0).getTitle());
					return f;
				}).flatMap(res -> ServerResponse.ok().body(BodyInserters.fromValue(res)));
	}

}
