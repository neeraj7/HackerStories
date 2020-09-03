package com.questionpro.hackerstories.model.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Story class.
 * 
 * @author neeraj.kumar
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Story extends Base{
	private int descendants;
	private int score;
	private String title;
	private String url;
}
