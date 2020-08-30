package com.questionpro.hackerstories.model;

import java.util.List;

import lombok.Data;

@Data
public class CommentResponse {
	
	private List<CommentModel> topComments;
}
