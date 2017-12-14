package com.thejach.voronoi;

public class VoronoiGenerator {

  private VoronoiAlgorithm algorithmImpl;

  public VoronoiGenerator(String algorithm, Points points, BoundingBox boundingBox) {
    if ("fortune".equals(algorithm)) {
      algorithmImpl = new FortuneAlgorithm(points, boundingBox);
    } else if ("custom".equals(algorithm)) {
      algorithmImpl = new CustomAlgorithm(points, boundingBox);
    }
    System.out.println("Using " + algorithm);
    System.out.println(String.format("First two points: (%f,%f) and (%f,%f)", points.xs[0], points.ys[0], points.xs[1], points.ys[1]));

    algorithmImpl.generate();
  }

  public Graph getGraph() {
    Graph g = algorithmImpl.getGraph();
    Edge e = g.getEdges().get(0);
    System.out.println(String.format("First edge: (%f,%f) to (%f,%f)", e.getX0(), e.getY0(), e.getX1(), e.getY1()));
    return g;
  }

}
