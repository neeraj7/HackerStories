package com.questionpro.hackerstories.model;

import lombok.Data;

/**
 * Error model to hold the error details.
 * 
 * @author neeraj.kumar
 *
 */
@Data
public class HackerError {
  private int errorStatus;
  private String errorCode;
  private String errorMsg;
}
