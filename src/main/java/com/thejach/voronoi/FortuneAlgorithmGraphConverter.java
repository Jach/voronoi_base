package com.thejach.voronoi;

import java.util.List;

import be.humphreys.simplevoronoi.GraphEdge;

public class FortuneAlgorithmGraphConverter implements AlgorithmConverter {

  private List<GraphEdge> edges;

  public FortuneAlgorithmGraphConverter(List<GraphEdge> edges) {
    this.edges = edges;
  }

  /**
   * The GraphEdge structure has its points 1-indexed and contains site information that we do not yet need
   * so this method converts it to our standard Graph form.
   */
  @Override
  public Graph convert() {
    Graph graph = new Graph();
    
    for (GraphEdge fortuneEdge : edges) {
      Edge edge = new Edge();
      edge.setX0(fortuneEdge.x1);
      edge.setX1(fortuneEdge.x2);
      edge.setY0(fortuneEdge.y1);
      edge.setY1(fortuneEdge.y2);
      graph.addEdge(edge);
    }
    return graph;
  }

}
