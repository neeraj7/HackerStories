package com.questionpro.hackerstories.model;

import lombok.Data;

/**
 * The CommentModel class.
 * Holds the data fields required for response.
 * 
 * @author neeraj.kumar
 *
 */
@Data
public class CommentModel {
	
	private String text;
	private String user;
	private int userProfileAge;

}
