package com.questionpro.hackerstories.model.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Comment class.
 * 
 * @author neeraj.kumar
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends Base {
	private int parent;
	private String text;
}
