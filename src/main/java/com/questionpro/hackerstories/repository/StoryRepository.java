package com.questionpro.hackerstories.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.questionpro.hackerstories.model.StoryModel;

@Repository
public interface StoryRepository extends ReactiveMongoRepository<StoryModel, String>{

}
