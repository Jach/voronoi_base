package com.thejach.voronoi;

public class Edge {
  private double x0, y0, x1, y1;
  
  public Edge() {
  }

  /**
   * Construct a new edge from the point (x0, y0) to the point (x1, y1)
   */
  public Edge(double x0, double y0, double x1, double y1) {
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
  }

  public double getX0() {
    return this.x0;
  }

  public double getY0() {
    return this.y0;
  }

  public double getX1() {
    return this.x1;
  }

  public double getY1() {
    return this.y1;
  }

  public void setX0(double x0) {
    this.x0 = x0;
  }

  public void setY0(double y0) {
    this.y0 = y0;
  }

  public void setX1(double x1) {
    this.x1 = x1;
  }

  public void setY1(double y1) {
    this.y1 = y1;
  }
}
