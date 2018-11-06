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
 * ====================
 * Your code goes here!
 * Generates the voronoi diagram by 
 * mutating this.graph.
 * ====================
 */
CustomAlgorithm.prototype.generate = function() {
  var g = new Graph(); // The graph holds the edges to draw

  // Extract out the bounds of the screen for convenience
  var min_x = this.bounding_box[0];
  var min_y = this.bounding_box[1];
  var max_x = this.bounding_box[2];
  var max_y = this.bounding_box[3];

  var points = this.site_points;

  // Extract the first two points (you are always guaranteed at least two points)
  var x0 = points[0][0];
  var y0 = points[0][1];
  var x1 = points[1][0];
  var y1 = points[1][1];

  // ADD YOUR CODE HERE!

  // Problem statement:
  // Observe the behavior of Voronoi diagrams generated from two Site points.
  // The result is an orthogonal line equidistant between them that bisects the plane
  // defined by the bounding box that the points are in.
  // Change the code below so that given two site points, the proper-looking graph
  // is drawn.

  // Some potentially useful formulas:
  // * Midpoint between two points (x0, y0) and (x1, y1) is the point ( (x0+x1)/2, (y0+y1)/2)
  // * Distance formula between two points is distance = sqrt((x0-x1)^2 + (y0-y1)^2)
  // * The slope between two points is the change in y over the change in x: (y1 - y0) / (x1 - x0)
  // * The perpendicular slope is: -1/slope
  // * There are different ways to represent a Line. One way is the slope-intercept form:
  //     y = mx + b
  //     inputs are m, the line slope, and b, the y-intercept
  // * The other is the point-slope form:
  //     y = m(x-a) + b
  //     inputs are m, the line slope, and some known point on the line (a, b)

  // The following code is for demoing the data and APIs and should be removed/commented out for
  // your solution.
  // The setup above is meant to help get you going, but you can change/remove it,
  // and add whatever additional functions you need, but ultimately you should
  // add edges to the graph to get things to draw.

  // "add_edge" is how you make line segments get drawn by the UI.
  // It takes four arguments -- the first two define the starting (x,y) point of the line segment,
  // and the last two define the ending (x,y) point of the line segment.
  // Here we draw two line segments from the corners of the draw screen (bounding box) to form an 'X'.
  g.add_edge(min_x, min_y, max_x, max_y);
  g.add_edge(min_x, max_y, max_x, min_y);

  // Here we draw an edge that connects the first two points.
  g.add_edge(x0, y0, x1, y1);

  // Note you can also draw points, not just line segments, by making an 'edge'
  // with the same start point and end point. Here's a point drawn
  // at the middle of the screen, down 5 pixels.
  g.add_edge(max_x/2, max_y/2-5, max_x/2, max_y/2-5);
  // Thinking in terms of which points should be drawn may be helpful if thinking in terms of
  // line segments isn't working...



  this.graph = g; // Make sure g is stored
};

