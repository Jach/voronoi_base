package com.thejach.voronoi;

public class CustomAlgorithm implements VoronoiAlgorithm {

  private Points points;
  private BoundingBox boundingBox;
  private Graph graph;

  /**
   * @param points - structure of equal length xs and ys with at least 2 items in each. (xs[i], ys[i]) form a point.
   * @param boundingBox - the plane is bounded by a box defined by the two diagonal points (minX, minY) and (maxX, maxY).
   */
  public CustomAlgorithm(Points points, BoundingBox boundingBox) {
    this.points = points;
    this.boundingBox = boundingBox;
  }

  @Override
  public void generate() {
    graph = new Graph();
    
    // Replace this code with your own!
    graph.addEdge(new Edge(boundingBox.minX, boundingBox.minY,
                           boundingBox.maxX, boundingBox.maxY));
    graph.addEdge(new Edge(boundingBox.minX, boundingBox.maxY,
                           boundingBox.maxX, boundingBox.minY));
    graph.addEdge(new Edge(points.xs[0], points.ys[0],
                           points.xs[1], points.ys[1]));
  }

  @Override
  public Graph getGraph() {
    return graph;
  }

}
