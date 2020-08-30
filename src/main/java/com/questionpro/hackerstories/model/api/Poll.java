package com.questionpro.hackerstories.model.api;

import java.util.List;

import lombok.Data;

@Data
public class Poll extends Base{

	private List<Integer> parts;
	private int descendants;
	private int score;
	private String title;
	private String text;
	
}
