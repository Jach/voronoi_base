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
    // The point-slope equation of a line is: y - b = m(x -a)
    //                                       inputs are m, the line slope, and the point (a, b)
    // The slope-intersect equation of a line is: y = mx + b
    //                                       inputs are m, the line slope, and b, the y-intercept

    // Tip: the Canvas gracefully handles out-of-bounds edge points and draws the right line anyway

    graph = new Graph();

    // Replace this code with your own!
    /*graph.addEdge(new Edge(boundingBox.minX, boundingBox.minY,
            boundingBox.maxX, boundingBox.maxY));
    graph.addEdge(new Edge(boundingBox.minX, boundingBox.maxY,
            boundingBox.maxX, boundingBox.minY));
    double x0 = points.xs[0];
    double y0 = points.ys[0];
    double x1 = points.xs[1];
    double y1 = points.ys[1];
    graph.addEdge(new Edge(x0, y0,
            x1, y1));
    */
    
    // Strategy:
    // Get the midpoint of the two points.
    // Compute the perp. slope of the line that connects the two.
    // Create a bisecting line of that slope for the edge, done!
    // Well, will then need to clamp.
    double x0 = points.xs[0];
    double y0 = points.ys[0];
    double x1 = points.xs[1];
    double y1 = points.ys[1];
    
    double midpointX = (x0 + x1) / 2; // Ignoring overflow potential
    double midpointY = (y0 + y1) / 2;
    
    if (x1 - x0 == 0.0) { // points are along same x, so can just draw a flat line from min --> midpoint y --> max
      graph.addEdge(new Edge(boundingBox.minX, midpointY, boundingBox.maxX, midpointY));
      return;
    }
    if (y1 - y0 == 0.0) { // along same y
      graph.addEdge(new Edge(midpointX, boundingBox.minY, midpointX, boundingBox.maxY));
      return;
    }
    double slopeBetweenPoints = (y1 - y0) / (x1 - x0);
    double perpSlope = -1/slopeBetweenPoints;
    
    // Using point-slope form of a line, we can construct two equations
    // that can accept an x and return the y on the line, then accept the y and return the x on the line.
    // Let's split out into two private methods.
    
    // Now we can add the edge by (ignoring bounding box issues for now) just finding out where the line intersects the y axis at x == min X (0),
    // which is guaranteed somewhere so long as the line is not vertical (which we handled above),
    // and where the line intersects the y axis at x == max X
    double startX = pointOnLineGivenY(boundingBox.minY, perpSlope, midpointX, midpointY);
    double endX = pointOnLineGivenY(boundingBox.maxY, perpSlope, midpointX, midpointY);
    
    double startY = pointOnLineGivenX(boundingBox.minX, perpSlope, midpointX, midpointY);
    double endY = pointOnLineGivenX(boundingBox.maxX, perpSlope, midpointX, midpointY);

    // The edge points will be on two of the four sides of the box to be bounded.
    double startEdgeX, startEdgeY, endEdgeX, endEdgeY;
    if (startX < boundingBox.minX) { // Starting point's x is out of bounds, we need to use startY set instead
      startEdgeX = boundingBox.minX;
      startEdgeY = startY;
    } else if (startX > boundingBox.maxX) {
      startEdgeX = boundingBox.maxX;
      startEdgeY = endY;
    } else {
      startEdgeX = startX;
      startEdgeY = boundingBox.minY;
    }
    if (endX > boundingBox.maxX) {
      endEdgeX = boundingBox.maxX;
      endEdgeY = endY;
    } else if (endX < boundingBox.minX) {
      endEdgeX = boundingBox.minX;
      endEdgeY = startY;
    } else {
      endEdgeX = endX;
      endEdgeY = boundingBox.maxY;
    }
      graph.addEdge(new Edge(startEdgeX, startEdgeY, endEdgeX, endEdgeY));
  }
  
  // given point-slope equation of a line for point (a, b) and slope m,
  // y - b = m(x - a)
  // return y = m(x - a) + b 
  private double pointOnLineGivenX(double x, double slope, double lineX, double lineY) {
    return slope * (x - lineX) + lineY;
  }
  
  // same as above, but now given
  // y - b = m(x - a)
  // solve for x so we can pass y
  // y - b + ma = mx
  // x = (y-b+ma)/m
  private double pointOnLineGivenY(double y, double slope, double lineX, double lineY) {
    return (y - lineY + slope*lineX) / slope;
  }

  @Override
  public Graph getGraph() {
    return graph;
  }

}
