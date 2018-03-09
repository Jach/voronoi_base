package com.thejach.voronoi;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    Set<GraphEdge> seenEdges = new TreeSet<>((o1, o2) -> o1.x1 == o2.x1 && o1.y1 == o2.y1 && o1.x2 == o2.x2 && o1.y2 == o2.y2 && o1.site1 == o2.site1 && o1.site2 == o2.site2 ? 0 : -1); // dedupe set
    
    for (GraphEdge fortuneEdge : edges) {
      if (seenEdges.contains(fortuneEdge)) {
        continue;
      }
      Edge edge = new Edge();
      edge.setX0(fortuneEdge.x1);
      edge.setX1(fortuneEdge.x2);
      edge.setY0(fortuneEdge.y1);
      edge.setY1(fortuneEdge.y2);
      edge.setSite1_i(fortuneEdge.site1);
      edge.setSite2_i(fortuneEdge.site2);
      graph.addEdge(edge);
      seenEdges.add(fortuneEdge);
    }
    return graph;
  }

}
