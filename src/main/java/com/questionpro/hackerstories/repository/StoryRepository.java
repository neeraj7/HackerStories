package com.questionpro.hackerstories.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.questionpro.hackerstories.model.StoryModel;

/**
 * The StoryRepository class.
 * A reactive repository for StoryModel.
 * 
 * @author neeraj.kumar
 *
 */
@Repository
public interface StoryRepository extends ReactiveMongoRepository<StoryModel, String>{

}
