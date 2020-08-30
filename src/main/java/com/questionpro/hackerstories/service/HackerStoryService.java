package com.questionpro.hackerstories.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.questionpro.hackerstories.model.CommentModel;
import com.questionpro.hackerstories.model.StoryModel;
import com.questionpro.hackerstories.model.StoryResponse;

import reactor.core.publisher.Mono;

@Component
public interface HackerStoryService {

	public Mono<StoryResponse> getBestStories();
	
	public List<StoryModel> getPastStories();
	
	public List<CommentModel> getTopCommentsOfGivenStory();
	
}
