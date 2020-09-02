package com.questionpro.hackerstories.model.api;

import lombok.Data;

/**
 * The Story class.
 * 
 * @author neeraj.kumar
 *
 */
@Data
public class Story extends Base{

	private int descendants;
	private int score;
	private String title;
	private String url;
}
