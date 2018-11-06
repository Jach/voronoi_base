package com.thejach.voronoi.fortune;

import java.util.List;

import com.thejach.voronoi.VoronoiAlgorithm;
import com.thejach.voronoi.converter.FortuneAlgorithmGraphConverter;
import com.thejach.voronoi.datastructures.BoundingBox;
import com.thejach.voronoi.datastructures.Graph;
import com.thejach.voronoi.datastructures.SitePoints;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

public class FortuneAlgorithm implements VoronoiAlgorithm {
  private SitePoints points;
  private BoundingBox boundingBox;
  private List<GraphEdge> generatedAllEdges;

  public FortuneAlgorithm(SitePoints points, BoundingBox boundingBox) {
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
