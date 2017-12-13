package com.thejach.voronoi;

import java.util.Optional;

import org.json.*;

/**
 * Handles parsing the user input on the /generate route into a trusted form.
 */
public class GenerateInputHandler {

  private Optional<Points> maybePoints = Optional.empty();
  private Optional<BoundingBox> maybeBoundingBox = Optional.empty();

  public void handle(String json) {
    try {
      JSONObject input = new JSONObject(json);
      JSONArray points = input.getJSONArray("points");

      double[] xs = new double[points.length()];
      double[] ys = new double[points.length()];

      for (int i = 0; i < points.length(); i++) {
        JSONArray point = points.getJSONArray(i);
        xs[i] = point.getDouble(0);
        ys[i] = point.getDouble(1);
      }

      JSONArray boundingBox = input.getJSONArray("bounding_box");

      double minX = boundingBox.getDouble(0);
      double minY = boundingBox.getDouble(1);
      double maxX = boundingBox.getDouble(2);
      double maxY = boundingBox.getDouble(3);

      maybePoints = Optional.of(new Points(xs, ys));
      maybeBoundingBox = Optional.of(new BoundingBox(minX, minY, maxX, maxY));
    } catch (JSONException e) {
      // Bail out, make sure points and boundingBox remain empty
      maybePoints = Optional.empty();
      maybeBoundingBox = Optional.empty();
    }

  }

  public Optional<Points> getPoints() {
    return maybePoints;
  }

  public Optional<BoundingBox> getBoundingBox() {
    return maybeBoundingBox;
  }

}
