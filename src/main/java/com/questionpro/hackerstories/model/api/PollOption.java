package com.questionpro.hackerstories.model.api;

import lombok.Data;

@Data
public class PollOption extends Base {
	
	private int parent;
	private int score;

}
