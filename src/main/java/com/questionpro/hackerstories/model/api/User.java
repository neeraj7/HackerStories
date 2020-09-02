package com.questionpro.hackerstories.model.api;

import java.util.List;

import lombok.Data;

/**
 * The User class.
 * 
 * @author neeraj.kumar
 *
 */
@Data
public class User {
	
	private String id;
	private int delay;
	private int created;
	private int karma;
	private String about;
	private List<Integer> submitted;

}
