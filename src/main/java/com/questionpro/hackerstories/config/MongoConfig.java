package com.questionpro.hackerstories.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

/**
 * Configuration class for MongoDB.
 * 
 * @author neeraj.kumar
 *
 */
@EnableReactiveMongoRepositories
public class MongoConfig extends AbstractReactiveMongoConfiguration {

	@Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }
 
    @Override
    protected String getDatabaseName() {
        return "reactive";
    }
}
