package com.thejach.voronoi;

import io.javalin.Javalin;
import io.javalin.embeddedserver.Location;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

import org.json.*;
import java.util.List;

public class Application {

  final static int PORT = 7171;

  public static void main(String[] args) {
    Javalin app = Javalin.create()
      .enableStaticFiles(System.getProperty("user.dir"), Location.EXTERNAL)
      .port(PORT)
      .start();

    app.post("/generate/:algorithm", ctx -> {
      String algo = ctx.param("algorithm");

      JSONObject input = new JSONObject(ctx.body());
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

      Voronoi v = new Voronoi(0.00001);
      List<GraphEdge> allEdges = v.generateVoronoi(xs, ys, minX, maxX, minY, maxY);

      ctx.json(allEdges);

    });

    System.out.println("\nStarted application on port " + PORT + ".");
    System.out.println("Visit http://localhost:" + PORT + "/ui.html to get started.");
    System.out.println("Note if " + System.getProperty("user.dir") + " is not the root directory " +
        "of the repository, you will need to restart in that proper directory for the link to work.");
  }
}
