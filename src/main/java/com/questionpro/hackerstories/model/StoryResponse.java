package com.questionpro.hackerstories.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoryResponse {

  private List<StoryModel> bestStories;

}
