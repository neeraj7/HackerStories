package com.questionpro.hackerstories.model.api;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Base {
	
	/**
	 * The item's unique id.
	 */
	@NotNull
	private String id;
	
	/**
	 * 'true' if the item is deleted.
	 */
	private boolean deleted;
	
	/**
	 * Type of item.
	 */
	@NotNull
	private ItemType type;
	
	/**
	 * The username of the item's author.
	 */
	private String by;
	
	/**
	 * Creation date of the item, in Unix time.
	 */
	private int time;
	
	/**
	 * 'true' if the item is dead.
	 */
	private boolean dead;
	
	/**
	 * The ids of the item's comments, in ranked display order
	 */
	private List<Integer> kids;

}

enum ItemType {
	job, story, comment, poll, pollopt;
}
