package com.thejach.voronoi;

import io.javalin.Javalin;
import io.javalin.Context;
import io.javalin.embeddedserver.Location;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

import org.json.*;
import java.util.List;
import java.util.Optional;

public class Application {

  final static int PORT = 7171;

  public static void main(String[] args) {
    Javalin app = Javalin.create()
      .enableStaticFiles(System.getProperty("user.dir"), Location.EXTERNAL)
      .port(PORT)
      .start();

    app.post("/generate/:algorithm", ctx -> handleGenerate(ctx));

    System.out.println("\nStarted application on port " + PORT + ".");
    System.out.println("Visit http://localhost:" + PORT + "/ui.html to get started.");
    System.out.println("Note if " + System.getProperty("user.dir") + " is not the root directory " +
        "of the repository, you will need to restart in that proper directory for the link to work.");
  }

  /**
   * Handles the endpoint.
   * Expected input shape:
   * ctx contains one param, algorithm, that can be used to determine a generator.
   * ctx has a JSON body with the same
   * {"points": [[x0, y0], [x1, y1]],
   * "bounding_box": [minX, minY, maxX, maxY]}
   * All values are numeric.
   *
   * Output:
   * If there is an error, the output will be the json object {"error": "Details"}
   * Otherwise, will be the json object {"edges": [edge1, edge2...]}
   * with each edge being a map {"x0": x0, "y0": y0, "x1": x1, "y1": y1}
   * each value being numeric and defining the edge lines starting at x0,y0 and ending at x1,y1.
   */
  public static void handleGenerate(Context ctx) {
      String algo = ctx.param("algorithm");

      GenerateInputHandler handler = new GenerateInputHandler();

      handler.handle(ctx.body());

      Optional<Points> maybePoints = handler.getPoints();
      Optional<BoundingBox> maybeBoundingBox = handler.getBoundingBox();

      if (!maybePoints.isPresent() || !maybeBoundingBox.isPresent()) {
        return;
      }

      Voronoi v = new Voronoi(0.00001);
      List<GraphEdge> allEdges = v.generateVoronoi(maybePoints.get().xs, maybePoints.get().ys, maybeBoundingBox.get().minX, maybeBoundingBox.get().maxX, maybeBoundingBox.get().minY, maybeBoundingBox.get().maxY);

      ctx.json(allEdges);

    }
}
