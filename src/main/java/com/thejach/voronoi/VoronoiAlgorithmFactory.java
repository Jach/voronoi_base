package com.thejach.voronoi;

import com.thejach.voronoi.custom.CustomAlgorithm;
import com.thejach.voronoi.datastructures.BoundingBox;
import com.thejach.voronoi.datastructures.SitePoints;
import com.thejach.voronoi.fortune.FortuneAlgorithm;

public class VoronoiAlgorithmFactory {

  public static VoronoiAlgorithm FortuneGenerator(SitePoints points, BoundingBox boundingBox) {
    return new FortuneAlgorithm(points, boundingBox);
  }
  
  public static VoronoiAlgorithm CustomGenerator(SitePoints points, BoundingBox boundingBox) {
    return new CustomAlgorithm(points, boundingBox);
  }

}
