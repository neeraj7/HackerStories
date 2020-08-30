package com.questionpro.hackerstories.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.questionpro.hackerstories.constants.Constants;
import com.questionpro.hackerstories.model.ApiResponse;
import com.questionpro.hackerstories.model.CommentModel;
import com.questionpro.hackerstories.model.StoryModel;
import com.questionpro.hackerstories.model.StoryResponse;
import com.questionpro.hackerstories.model.api.Story;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class HackerStoryServiceImpl implements HackerStoryService {

	private WebClient client = WebClient.create();

	@Override
	public Mono<StoryResponse> getBestStories() {

		return client.get().uri(Constants.BEST_STORIES_URL).accept(MediaType.APPLICATION_JSON)
				.exchange().flatMap(res -> res.bodyToMono(Integer[].class))
				.map(f -> {
					System.out.println("first item in array: " + f[0]);
					return f;
				})
				.flatMap(res -> fetchItems(res));
	}

	private Mono<StoryResponse> fetchItems(Integer[] items) {

		return Flux.fromArray(items)
				.take(10)
				.map(f -> {
					System.out.println("item: " + f);
					return f;
				})
				.flatMap(item -> client.get().uri(Constants.ITEM_URL.concat(item + Constants.DOT_JSON))
						.accept(MediaType.APPLICATION_JSON).exchange().flatMap(res -> res.bodyToMono(Story.class)))
				.map(story -> {
					// set the story model from story response
					StoryModel sm = new StoryModel();
					sm.setScore(story.getScore());
					sm.setTime(story.getTime());
					sm.setUrl(story.getUrl());
					sm.setTitle(story.getTitle());
					sm.setUser(story.getBy());
					return sm;
				})
				.map(f -> {
					System.out.println("title: " + f.getTitle());
					return f;
				})
				.collect(Collectors.toList())
				.flatMap(list -> {
					StoryResponse sr = new StoryResponse();
					sr.setBestStories(list);
					return Mono.just(sr);
				});
	}

	@Override
	public List<StoryModel> getPastStories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentModel> getTopCommentsOfGivenStory() {
		// TODO Auto-generated method stub
		return null;
	}

}
