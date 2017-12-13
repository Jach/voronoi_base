package com.thejach.voronoi;

import java.util.List;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

public class FortuneAlgorithm implements VoronoiAlgorithm {
  private Points points;
  private BoundingBox boundingBox;
  private List<GraphEdge> generatedAllEdges;

  public FortuneAlgorithm(Points points, BoundingBox boundingBox) {
    this.points = points;
    this.boundingBox = boundingBox;
  }

  @Override
  public void generate() {
    Voronoi v = new Voronoi(0.00001);
    generatedAllEdges = v.generateVoronoi(points.xs, points.ys,
        boundingBox.minX, boundingBox.maxX, boundingBox.minY, boundingBox.maxY);
  }
  
  @Override
  public Graph getGraph() {
    FortuneAlgorithmGraphConverter converter = new FortuneAlgorithmGraphConverter(generatedAllEdges);
    return converter.convert();
  }
}
