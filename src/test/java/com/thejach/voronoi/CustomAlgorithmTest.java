package com.thejach.voronoi;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomAlgorithmTest {

  @Test
  public void testGeneratesOutput() {
    double xs[] = {10, 20};
    double ys[] = {10, 20};
    SitePoints points = new SitePoints(xs, ys);
    BoundingBox boundingBox = new BoundingBox(0, 0, 100, 100);
    CustomAlgorithm algo = new CustomAlgorithm(points, boundingBox);
    algo.generate();
    assertTrue(algo.getGraph().getEdges().size() > 0);
  }

}
