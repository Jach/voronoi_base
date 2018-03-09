/**
 * Graph object, contains a list of edges.
 */
function Graph() {
  this.edges = [];
}

/**
 * Adds an edge to the list of edges.
 * An edge defines a line segment between the two points
 * (edge_start_x, edge_start_y) and (edge_end_x, edge_end_y).
 *
 * Optionally, you can specify site1_i and site2_i as indices
 * of the two site points on either side of the edge.
 */
Graph.prototype.add_edge = function(
  edge_start_x, edge_start_y,
  edge_end_x, edge_end_y,
  site1_i, site2_i) {
  this.edges.push({x0: edge_start_x, y0: edge_start_y,
                  x1: edge_end_x, y1: edge_end_y,
                 site1_i: site1_i || -1, site2_i: site2_i || -1});
};

/**
 * CustomAlgorithm object, takes a list of site points and a bounding box
 * in the form [ [x, y], [x, y], ...] and [min_x, min_y, max_x, max_y].
 * There must be two or more points in site_points.
 */
function CustomAlgorithm(site_points, bounding_box) {
  this.site_points = site_points;
  this.bounding_box = bounding_box;
}

/**
 * Your code goes here!
 * Generates the voronoi diagram by inserting
 * edges into this.graph.
 */
CustomAlgorithm.prototype.generate = function() {
  var g = new Graph();
  var min_x = this.bounding_box[0];
  var min_y = this.bounding_box[1];
  var max_x = this.bounding_box[2];
  var max_y = this.bounding_box[3];
  var points = this.site_points;

  // Add your code here.
  // Note that both line segments and points are allowed.

  // 3 demo edges forming an 'X' across the screen and a segment joining the first two input points
  g.add_edge(min_x, min_y, max_x, max_y);
  g.add_edge(min_x, max_y, max_x, min_y);
  var x0 = points[0][0];
  var y0 = points[0][1];
  var x1 = points[1][0];
  var y1 = points[1][1];
  g.add_edge(x0, y0, x1, y1);

  // 1 demo point rendering at the middle of the screen, down 5
  g.add_edge(max_x/2, max_y/2-5, max_x/2, max_y/2-5);

  this.graph = g; // Make sure g is stored
};

