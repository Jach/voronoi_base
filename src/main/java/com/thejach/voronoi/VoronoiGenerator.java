package com.thejach.voronoi;

public class VoronoiGenerator {

  private VoronoiAlgorithm algorithmImpl;

  public VoronoiGenerator(String algorithm, Points points, BoundingBox boundingBox) {
    if ("fortune".equals(algorithm)) {
      algorithmImpl = new FortuneAlgorithm(points, boundingBox);
    } else if ("custom".equals(algorithm)) {
      algorithmImpl = new CustomAlgorithm(points, boundingBox);
    }

    algorithmImpl.generate();
  }

  public Graph getGraph() {
    return algorithmImpl.getGraph();
  }

}
