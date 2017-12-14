package com.thejach.voronoi;

public class CustomAlgorithm implements VoronoiAlgorithm {

  private final Points points;
  private final BoundingBox boundingBox;
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
    // Problem statement:

    // Observe the behavior of Voronoi diagrams generated from two points.
    // The result is an orthogonal line equidistant between them that bisects the plane
    // defined by the bounding box that the points are in.
    // Given two points defined in points.xs, points.ys, change the code below
    // so that the graph structure contains one edge line: the line that bisects
    // the plane between the two points.

    // Useful formulas:
    // Midpoint formula between two points (x0, y0) and (x1, y1) is the point ( (x0+x1)/2, (y0+y1)/2 )
    // The slope-form equation of a line is: y - b = m(x -a)
    //                                       inputs are m, the line slope, and the point (a, b)
    // The slope-intersect equation of a line is: y = mx + b
    //                                       inputs are m, the line slope, and b, the y-intercept

    // Tip: the Canvas gracefully handles out-of-bounds edge points and draws the right line anyway

    graph = new Graph();

    // Replace this code with your own!
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
  }

  @Override
  public Graph getGraph() {
    return graph;
  }

}
