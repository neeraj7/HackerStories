package com.questionpro.hackerstories.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The StoryResponse class.
 * Wraps the list of StoryModels for response.
 * 
 * @author neeraj.kumar
 *
 */
@Data
@NoArgsConstructor
public class StoryResponse {

  private List<StoryModel> bestStories;

}
