package com.thejach.voronoi.datastructures;

public class Edge {
  private double x0, y0, x1, y1;
  
  private int site1_i;
  private int site2_i;
  
  public Edge() {
  }

  /**
   * Construct a new edge from the point (x0, y0) to the point (x1, y1)
   */
  public Edge(double x0, double y0, double x1, double y1) {
    this(x0, y0, x1, y1, -1, -1);
  }
  
  /**
   * Constructs a new edge, also annotates the array indices of the two site points on either side of the edge.
   */
  public Edge(double x0, double y0, double x1, double y1, int site1_i, int site2_i) {
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
    this.setSite1_i(site1_i);
    this.setSite2_i(site2_i);
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

  public int getSite1_i() {
    return site1_i;
  }

  public void setSite1_i(int site1_i) {
    this.site1_i = site1_i;
  }

  public int getSite2_i() {
    return site2_i;
  }

  public void setSite2_i(int site2_i) {
    this.site2_i = site2_i;
  }
}
