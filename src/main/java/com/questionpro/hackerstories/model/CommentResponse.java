package com.questionpro.hackerstories.model;

import java.util.List;

import lombok.Data;

/**
 * The CommentResponse class.
 * Wraps the list of CommentModels for response.
 * 
 * @author neeraj.kumar
 *
 */
@Data
public class CommentResponse {
	
	private List<CommentModel> topComments;
}
