package com.questionpro.hackerstories.model.api;

import lombok.Data;

/**
 * The Comment class.
 * 
 * @author neeraj.kumar
 *
 */
@Data
public class Comment extends Base {
	private int parent;
	private String text;
}
