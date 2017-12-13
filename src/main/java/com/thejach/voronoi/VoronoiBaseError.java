package com.thejach.voronoi;

public class VoronoiBaseError {

  private String errorMessage;

  public VoronoiBaseError(String errorMessage) {
    this.setErrorMessage(errorMessage);
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

}
