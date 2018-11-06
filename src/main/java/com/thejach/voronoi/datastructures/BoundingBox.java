package com.thejach.voronoi.datastructures;

public class BoundingBox {
  public double minX;
  public double minY;
  public double maxX;
  public double maxY;

  public BoundingBox(double minX, double minY, double maxX, double maxY) {
    this.minX = minX;
    this.minY = minY;
    this.maxX = maxX;
    this.maxY = maxY;
  }
}

