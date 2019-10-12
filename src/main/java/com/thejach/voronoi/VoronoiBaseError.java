package com.thejach.voronoi;

public class VoronoiBaseError extends Throwable {

    private static final long serialVersionUID = 7621743459208647479L;

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
