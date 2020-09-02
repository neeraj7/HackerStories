package com.questionpro.hackerstories.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * The StoryModel class.
 * Holds the data fields required for response and DB.
 * 
 * @author neeraj.kumar
 *
 */
@Data
@Document
public class StoryModel {

	@Id
	@JsonIgnore
	private String id;
	private String title;
	private String url;
	private int score;
	private int time;
	private String user;

}
