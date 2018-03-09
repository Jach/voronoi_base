package com.thejach.voronoi;

import io.javalin.Javalin;
import io.javalin.Context;
import io.javalin.embeddedserver.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

public class Application {

  final static int PORT = 7171;

  public static void main(String[] args) {
    Javalin app = Javalin.create()
      .enableStaticFiles(System.getProperty("user.dir"), Location.EXTERNAL)
      .port(PORT)
      .start();

    app.post("/generate/:algorithm", ctx -> handleGenerate(ctx));
    
    app.get("/alive", ctx -> ctx.result("true"));
    
    app.post("/colorize/colormap", ctx -> handleColorizerColorMap(ctx));

    System.out.println("\nStarted application on port " + PORT + ".");
    System.out.println("Visit http://localhost:" + PORT + "/ui.html to get started.");
    System.out.println("Note if " + System.getProperty("user.dir") + " is not the root directory " +
        "of the repository, you will need to restart in that proper directory for the link to work.");
  }

  /**
   * Expected input shape:
   * ctx has a JSON body in the request that contains {'edges': edge_list, 'sites': n}
   * The edge_list is the same as the output of {@link #handleGenerate(Context)}.
   * i.e. a list of edge maps. The key difference is each edge MUST contain a site1_i and site2_i. 'sites' is an integer for how mamny sites in total.
   * 
   * Output:
   * A simple colormap JSON object:
   * {"colormap": {"siteIndexAsStr": "#abcdef", ...} }
   * i.e. each siteIndex is mapped to a hexadecimal color.
   */
  private static void handleColorizerColorMap(Context ctx) {
    JSONObject input = new JSONObject(ctx.body());
    JSONArray edgesJson = input.getJSONArray("edges");
    int sites = input.getInt("sites");

    List<Edge> edges = new ArrayList<>();
    for (int i = 0; i < edgesJson.length(); i++) {
      JSONObject el = edgesJson.getJSONObject(i);
      edges.add(new Edge(el.getDouble("x0"),
          el.getDouble("y0"),
          el.getDouble("x1"),
          el.getDouble("y1"),
          el.getInt("site1_i"),
          el.getInt("site2_i")));
    }
    GraphColorizer gc = new GraphColorizer(edges, sites);
    
    ctx.json(gc.getColormap());
  }

  /**
   * Handles the endpoint.
   * Expected input shape:
   * ctx contains one param, algorithm, that can be used to determine a generator.
   * ctx has a JSON body with the same
   * {"sitePoints": [[x0, y0], [x1, y1]],
   * "bounding_box": [minX, minY, maxX, maxY]}
   * All values are numeric.
   *
   * Output:
   * If there is an error, the output will be the json object {"error": "Details"}
   * Otherwise, will be the json object {"edges": [edge1, edge2...]}
   * with each edge being a map {"x0": x0, "y0": y0, "x1": x1, "y1": y1} (optionally site1_i and site2_i may be part of this map)
   * each value being numeric and defining the edge lines starting at x0,y0 and ending at x1,y1.
   */
  public static void handleGenerate(Context ctx) {
    GenerateInputHandler handler = new GenerateInputHandler();
    handler.handle(ctx.body());

    Optional<SitePoints> maybePoints = handler.getSitePoints();
    Optional<BoundingBox> maybeBoundingBox = handler.getBoundingBox();

    if (!maybePoints.isPresent() || !maybeBoundingBox.isPresent()) {
      ctx.json(new VoronoiBaseError("Both points and a bounding box must be supplied."));
      return;
    }

    if (maybePoints.get().xs.length < 2) {
      ctx.json(new VoronoiBaseError("Must supply two or more points."));
      return;
    }

    VoronoiGenerator gen = new VoronoiGenerator(ctx.param("algorithm"), maybePoints.get(), maybeBoundingBox.get());
    ctx.json(gen.getGraph());
  }
}
