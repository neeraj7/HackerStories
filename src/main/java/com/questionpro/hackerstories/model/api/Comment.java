package com.questionpro.hackerstories.model.api;

import lombok.Data;

@Data
public class Comment extends Base {
	private int parent;
	private String text;
}
