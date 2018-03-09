package com.thejach.voronoi;

public class CustomAlgorithm implements VoronoiAlgorithm {

  private SitePoints points;
  private BoundingBox boundingBox;
  private Graph graph;

  /**
   * @param points - structure of equal length xs and ys with at least 2 items in each. (xs[i], ys[i]) form a site point.
   * @param boundingBox - the plane is bounded by a box defined by the two diagonal points (minX, minY) and (maxX, maxY).
   */
  public CustomAlgorithm(SitePoints points, BoundingBox boundingBox) {
    this.points = points;
    this.boundingBox = boundingBox;
  }

  @Override
  public void generate() {
    graph = new Graph();
    
    // Replace this code with your own!
    // Note that both line segments and points are allowed.

    // 3 demo edges forming an 'X' across the screen and a segment joining the first two input points
    graph.addEdge(new Edge(boundingBox.minX, boundingBox.minY,
                           boundingBox.maxX, boundingBox.maxY));
    graph.addEdge(new Edge(boundingBox.minX, boundingBox.maxY,
                           boundingBox.maxX, boundingBox.minY));
    double x0 = points.xs[0];
    double y0 = points.ys[0];
    double x1 = points.xs[1];
    double y1 = points.ys[1];
    graph.addEdge(new Edge(x0, y0,
                           x1, y1));
    
    // 1 demo point rendering at the middle of the screen, down 5
    graph.addEdge(new Edge(boundingBox.maxX/2, boundingBox.maxY/2-5, boundingBox.maxX/2, boundingBox.maxY/2-5));
    
  }

  @Override
  public Graph getGraph() {
    return graph;
  }

}
