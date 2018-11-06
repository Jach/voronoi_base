package com.thejach.voronoi.datastructures;

import java.util.HashMap;
import java.util.Map;

public class Colormap {
  
  private Map<String, String> colormap;
  
  public Colormap() {
    colormap = new HashMap<>();
  }
  
  public void addColor(int siteIndex, String color) {
    colormap.put("" + siteIndex, color);
  }
  
  public Map<String, String> getColormap() {
    return colormap;
  }

}
