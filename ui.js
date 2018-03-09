/**
Note the UI should look something like this.
+-------------------------+-----------+
|                         |           |
|                         | +-------+ |
|  x                      | | Add   | |
|                         | +-------+ |
|                         |           |
|                         | +-------+ |
|          x              | | Remove| |
|                         | +-------+ |
|                         |           |
|                         | +-------+ |
|                   x     | | Clear | |
|                         | +-------+ |
|      x                  |           |
|                         | +-------+ |
|                         | |Load #1| |
|                         | +-------+ |
|                         |           |
|                         | +-------+ |
|                         | |Custom | |
|                         | +-------+ |
|                 x       |           |
|                         | +-------+ |
|                         | |Fortune| |
|                         | +-------+ |
|        x                |           |
|                         | +-+       |
|                         | | |Color? |
|                         | +-+       |
|                         |           |
+-------------------------+-----------+
*/

// Pixels that site points expand from to prevent duplicate slots (at 1) and too close neighbor site points (at > 1)
// Site Points themselves may still be drawn at some size < this.
var POINT_RADIUS = 10;

var POINT_DRAW_RADIUS = 3;

// 'add' or 'remove' modes to change the canvas.
var point_mode = 'add';

// determines whether JS functions or server calls are made
var is_offline = false;

// determines whether graphs from fortune/custom algorithm generates
// erase each other before drawing themselves
var overlay_graphs = false;

// determines whether we try to call a colorizer algorithm
// after generating the diagram
var fill_cells_with_color = false;

// convenient canvas handle.
var canvas = null;

// List of [x, y] pairs for each added site point.
var site_points = [];

/*
Sets everything up.
Fully constructs the Canvas,
then wires up all the event handlers.
*/
function setup() {
  canvas = document.getElementById('canvas');
  canvas.classList.add('clickable');
  var ctx = canvas.getContext('2d');

  canvas.addEventListener('click', on_click_canvas);
  canvas.addEventListener('mousemove', on_mouseover_canvas);

  var add_site_points = document.getElementById('add_site_points');
  add_site_points.checked = true; // Reset in case of refresh
  add_site_points.addEventListener('click', on_click_add_mode);
  document.getElementById('remove_site_points').addEventListener('click', on_click_remove_mode);

  document.getElementById('clear_site_points').addEventListener('click', on_click_clear_site_points);
  document.onkeyup = on_shortcut_keys;

  document.getElementById('load_preset_1').addEventListener('click', on_click_load_preset_1);
  document.getElementById('load_preset_2').addEventListener('click', on_click_load_preset_2);

  document.getElementById('run_custom').addEventListener('click', on_click_run_custom);

  document.getElementById('run_fortune').addEventListener('click', on_click_run_fortune);

  document.getElementById('fill_cells_with_color').checked = false;
  document.getElementById('fill_cells_with_color').addEventListener('click', on_click_fill_cells_with_color);

  document.getElementById('offline_mode').addEventListener('click', on_click_offline_mode);
  check_offline();

  document.getElementById('overlay_graphs').checked = false; // override potential browser reload save
  document.getElementById('overlay_graphs').addEventListener('click', on_click_overlay_graphs);

  redraw_canvas();
}

/*
When in add mode, records and draws the point, if the point doesn't intersect within POINT_RADIUS of an existing point.
When in remove mode, if there is a point that is clicked on that does intersect within POINT_RADIUS, remove that point.
*/
function on_click_canvas(e) {
  var point = canvas_pos(e);
  if (point_mode === 'add') {
    if (point_in_bounds(point) && // restrain site points from being right up against the edge
        find_point(point) === undefined && // if point hasn't been added
        find_nearby_point(point) === undefined) { // and point isn't too close
          site_points.push(point);
          redraw_canvas();
    }
  } else if (point_mode === 'remove') {
    var nearby_point = find_nearby_point(point);
    if (nearby_point !== undefined) {
      site_points.splice(nearby_point, 1);
      redraw_canvas();
    }
  }
}

function on_click_add_mode(e) {
  point_mode = 'add';
  canvas.classList.remove('clickable'); // Prevent duplicate adds
  canvas.classList.add('clickable');
}

function on_click_remove_mode(e) {
  point_mode = 'remove';
  canvas.classList.remove('clickable');
}

function on_click_clear_site_points(e) {
  site_points = [];
  redraw_canvas();
}

function on_shortcut_keys(e) {
  var key = e.key.toLowerCase();
  if (key === 'c') {
    on_click_clear_site_points();
  } else if (key === 'a') {
    on_click_run_custom();
  } else if (key === 'f') {
    on_click_run_fortune();
  } else if (key === '1') {
    on_click_load_preset_1();
  } else if (key === '2') {
    on_click_load_preset_2();
  }
}

function on_click_load_preset_1(e) {
  site_points = [[200,150], [500,450]];
  redraw_canvas();
}

function on_click_load_preset_2(e) {
  site_points = [[300,150], [500,150], [400,450]];
  redraw_canvas();
}

function on_click_run_custom(e) {
  e = e || {};
  e.target = e.target || {};
  e.target.disabled = true;
  request_generate('custom', function(data) {
    e.target.disabled = false;
    draw_voronoi(data, 'custom');
  });
}

function on_click_run_fortune(e) {
  e = e || {};
  e.target = e.target || {};
  e.target.disabled = true;
  request_generate('fortune', function(data) {
    e.target.disabled = false;
    draw_voronoi(data, 'fortune');
  });
}

function on_click_fill_cells_with_color(e) {
  fill_cells_with_color = e.target.checked;
}

function on_click_offline_mode(e) {
  is_offline = e.target.checked;
  document.getElementById('offline_mode').checked = is_offline;
}

/**
 * Makes a request to /alive. If a positive success is returned,
 * then assume we're not offline. Otherwise we go into offline mode.
 */
function check_offline() {
  var request = new XMLHttpRequest();
  request.open('GET', '/alive', true);
  request.onload = function() {
    if (this.status >= 200 && this.status < 400) {
      on_click_offline_mode({target: {checked: false}});
    } else {
      // some error
      on_click_offline_mode({target: {checked: true}});
    }
  };
  request.onerror = function() {
    on_click_offline_mode({target: {checked: true}});
  };

  request.send();
}

function on_click_overlay_graphs(e) {
  overlay_graphs = e.target.checked;
}

/*
When in 'remove' mode, responsible for changing the mouse cursor state to clickable when over a point...
*/
function on_mouseover_canvas(e) {
  if (point_mode === 'remove') {
    // TODO
  }
}

/*
Redraws canvas with only the points in the site_points array drawn.
@param skip_blank - skips whiting out the screen first
*/
function redraw_canvas(skip_blank) {
  var ctx = canvas.getContext('2d');

  if (skip_blank !== true) {
    ctx.fillStyle = '#ffffff';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
  }

  ctx.fillStyle = '#000000';

  ctx.strokeRect(0, 0, canvas.width, canvas.height);

  for (var i = 0; i < site_points.length; i++) {
    var point = site_points[i];
    ctx.beginPath();
    ctx.arc(point[0], point[1], POINT_DRAW_RADIUS, 0, Math.PI * 2, 1);
    ctx.fill();
  }

}

/*
 * Draws the voronoi diagram by using the edge data in graph to draw lines from each edge, which is a pair of points (x0, y0) to (x1, y1).
 *
 * @param graph_data - an object containing a structure like {'edges': [{x0: x0, y0: y0, x1: x1, y1: y1}, ...]}
 *                or if there is a known error, an object containing {'errorMessage': 'error message'}
 * @param algo - algorithm used to generate graph -- if 'fortune', draws thicker red lines
 */
function draw_voronoi(graph_data, algo) {
  graph_data = graph_data || {};
  if (!graph_data.hasOwnProperty('edges')) {
    var error = graph_data.errorMessage || 'An unknown error has occurred.';
    show_error_message(error);
    return;
  }

  redraw_canvas(overlay_graphs);

  var edges = graph_data.edges;
  if (fill_cells_with_color && edges[0].site1_i != -1 && edges[0].site1_i !== undefined) {
    colorize_voronoi(filter_points(edges));
  }

  var ctx = canvas.getContext('2d');
  ctx.strokeStyle = '#000000';
  ctx.lineWidth = 1;
  if (algo === 'fortune') {
    ctx.strokeStyle = '#ff0000';
    ctx.lineWidth = 5;
  }
  ctx.beginPath();

  for (var i = 0; i < edges.length; i++) {
    var edge = edges[i];
    if (is_edge_border(edge) || is_edge_on_border(edge)) {
      continue; // JS fortune's algo includes edge segments along the bb...
    }
    if (edge.x0 === edge.x1 && edge.y0 === edge.y1) { // same points, draw pixel
      ctx.stroke(); // end any lines..
      ctx.beginPath();
      ctx.arc(edge.x0, edge.y0, 1, 0, Math.PI * 2, 1);
      ctx.fill();
      ctx.beginPath(); // setup for next iteration
    } else {
      ctx.moveTo(edge.x0, edge.y0);
      ctx.lineTo(edge.x1, edge.y1);
    }
  }
  ctx.stroke();

  // reset to default
  ctx.strokeStyle = '#000000';
  ctx.lineWidth = 1;

}

function colorize_voronoi(edges) {
  var ctx = canvas.getContext('2d');
  for (var si = 0; si < site_points.length; si++) {
    var col = randColor();
    //console.log('%cSite ' + si, 'color: ' + col + ';');
    var drawing_edges = find_edges_next_to_site(edges, si);
    //console.log('Drawing edges', drawing_edges);
    var ordered_path = order_path_for_polygon(drawing_edges);
    //console.log('Ordered path', ordered_path);

    ctx.fillStyle = col;
    ctx.beginPath();
    ctx.moveTo(ordered_path[0][0], ordered_path[0][1]);
    for (var i = 1; i < ordered_path.length; i++) {
      ctx.lineTo(ordered_path[i][0], ordered_path[i][1]);
    }
    ctx.fill();
  }
  redraw_canvas(true);
}

/**
 * Remove any edges that are points...
 */
function filter_points(edges) {
  return edges.filter(function(e) { return !(e.x0 === e.x1 && e.y0 === e.y1); });
}

function randColor() {
  var hex = '0123456789abcdef';
  col = '#';
  for (var i = 0; i < 6; i++) {
    col += hex[Math.floor(Math.random()*hex.length)];
  }
  return col;
}

function fleq(fl1, fl2) {
  return Math.abs(fl1 - fl2) < 0.0001;
}

/**
 * Looks through edges to filter where an edge has a site index
 * equal to site_i. Returns those edges.
 * (Future optimization: remove those edges from either caller side or
 * here (making it no longer functional) to avoid duplicate search.
 * Or construct a lookup table.)
 */
function find_edges_next_to_site(edges, site_i) {
  return edges.filter(function(edge) { return edge.site1_i === site_i || edge.site2_i === site_i; });
}

/**
 * Given edges in any order, returns an ordered version that forms a
 * polygon loop: [ [x, y], [x, y], [x, y] ] would form a connected triangle.
 * You can draw it starting at the first pair, drawing to the second,
 * then drawing to the third. (From there an implicit draw back to the first
 * forms the completed shape.)
 *
 * Currently has some buggy behavior of oedges does not contain
 * enough edges to form a full loop. e.g. the Fortune algo on the server
 * does not give line segments that lie on the edge, however the JS
 * algo does, recommend you use 'offline' mode for coloring attempts.
 */
function order_path_for_polygon(oedges) {
  var edges = JSON.parse(JSON.stringify(oedges));
  var needs_edges_for_loop = true;
  // If any edge starts at a boundary line on x or y, start with that one.
  // Otherwise start with the first one since we should have a loop already.
  var idx = edges.findIndex(edge_touches_border);
  if (idx === -1) {
    idx = 0;
    needs_edges_for_loop = false;
  }
  var first = edges.splice(idx, 1)[0];
  var start_point;
  var prev;
  if (needs_edges_for_loop) {
    start_point = get_edge_touches_border(first);
    prev = get_other_edge_when_touches_border(first);
  } else {
    start_point = [first.x0, first.y0];
    prev = [first.x1, first.y1];
  }

  var sorted_edges = [start_point, prev];
  while (edges.length > 0) {
    var next_idx = find_connected_edge(edges, prev);
    if (next_idx === -1) {
      console.log('Failed to find connected edge given for ', prev[0], prev[1]);
      break;
    }
    var next = get_opposite_edge_from_connected(edges[next_idx], prev);
    edges.splice(next_idx, 1);
    sorted_edges.push(next);
    prev = next;
  }

  if (needs_edges_for_loop) {
    //sorted_edges.push(sorted_edges[0]);
  }
  return sorted_edges;
}

/**
 * If either of e's (x0, y0) or (x1, y0) touches the border,
 * returns the pair as an array of [x, y]. If neither do,
 * returns null.
 */
function get_edge_touches_border(e) {
  if (e.x0 === 0 || e.x0 === canvas.width || e.y0 === 0 || e.y0 === canvas.height) {
    return [e.x0, e.y0];
  } else if (e.x1 === 0 || e.x1 === canvas.width || e.y1 === 0 || e.y1 === canvas.height) {
    return [e.x1, e.y1];
  }
  return null;
}

function get_other_edge_when_touches_border(e) { // gets the opposite edge of above
  if (e.x0 === 0 || e.x0 === canvas.width || e.y0 === 0 || e.y0 === canvas.height) {
    return [e.x1, e.y1];
  } else if (e.x1 === 0 || e.x1 === canvas.width || e.y1 === 0 || e.y1 === canvas.height) {
    return [e.x0, e.y0];
  }
  return null;
}

function edge_touches_border(e) {
  return get_edge_touches_border(e) !== null;
}

/**
 * Returns index of edges array where a point matches either
 * end of the edge.
 */
function find_connected_edge(edges, point) {
  return edges.findIndex(function(e) { 
    return fleq(e.x0, point[0]) && fleq(e.y0, point[1]) || fleq(e.x1, point[0]) && fleq(e.y1, point[1]);
  });
}

/**
 * Given point that matches either (x0,y0) or (x1,y1),
 * return the opposite that doesn't match.
 */
function get_opposite_edge_from_connected(edge, point) {
  if (fleq(edge.x0, point[0]) && fleq(edge.y0, point[1])) {
    return [edge.x1, edge.y1];
  } else if (fleq(edge.x1, point[0]) && fleq(edge.y1, point[1])) {
    return [edge.x0, edge.y0];
  }
  return null;
}

/**
 * True if edge represents line segment of any one of the 4 sides
 * of the canvas.
 */
function is_edge_border(edge, already_swapped) {
  return (edge.x0 === 0 && edge.y0 === 0 && edge.x1 === canvas.width && edge.y1 === 0) || // top
    (edge.x0 === 0 && edge.y0 === 0 && edge.x1 === 0 && edge.y1 === canvas.height) || // left
    (edge.x0 === canvas.width && edge.y0 === 0 && edge.x1 === canvas.width && edge.y0 === canvas.height) || // right
    (edge.x0 === 0 && edge.y0 === canvas.height && edge.x1 === canvas.width && edge.y1 === canvas.height) || // bottom
    (!already_swapped && is_edge_border({x0: edge.x1, y0: edge.y1, x1: edge.x0, y1: edge.y0}, true)); // if edge is backwards
}

/**
 * True if edge is drawn along a border, i.e. xs match 0 or width or ys match 0 or width.
 */
function is_edge_on_border(edge) {
  return (edge.x0 === 0 && edge.x1 === 0 || edge.x0 === canvas.width && edge.x1 === canvas.width) ||
    (edge.y0 === 0 && edge.y1 === 0 || edge.y0 === canvas.height && edge.y1 === canvas.height);
}

/**
 * Sends fortune/custom request up to server (if online, otherwise delegates
 * locally) and sends response data back through cb.
 * Expected response for cb should be
 * something like {edges: edge_list}.
 */
function request_generate(algorithm, cb) {
  if (is_offline) {
    request_offline_generate(algorithm, cb);
    return;
  }
  var request = new XMLHttpRequest();
  request.open('POST', '/generate/' + algorithm, true);
  request.onload = function() {
    if (this.status >= 200 && this.status < 400) {
      cb(JSON.parse(this.response));
    } else {
      // some error
      cb(undefined);
    }
  };
  request.onerror = function() {
    cb(undefined);
  };

  request.send(JSON.stringify({
    site_points: site_points,
    bounding_box: [0, 0, canvas.width, canvas.height]
  }));
}

/**
 * Calls out to bundled JS implementation of fortune's algorithm,
 * or the custom algorithm defined in custom_algorithm.js.
 */
function request_offline_generate(algorithm, cb) {
  if (algorithm === 'fortune') {
    var voronoi = new Voronoi();
    var bbox = {xl: 0, yt: 0, xr: canvas.width, yb: canvas.height};
    var sites = site_points.map(function(p, pi) { return {x: p[0], y: p[1], pi: pi}; });
    var diagram = voronoi.compute(sites, bbox);
    var edges = diagram.edges.map(function(e) { return {x0: e.va.x, y0: e.va.y, x1: e.vb.x, y1: e.vb.y, site1_i: (e.lSite||{}).pi, site2_i: (e.rSite||{}).pi}; });

    cb({edges: edges});
  } else {
    var custom = new CustomAlgorithm(site_points, [0, 0, canvas.width, canvas.height]);
    custom.generate();
    cb({edges: custom.graph.edges});
  }
}

function show_error_message(msg) {
  alert(msg);
}

/*
Gets the position of the event's x,y on the canvas itself.
*/
function canvas_pos(e) {
  return [e.clientX - canvas.offsetLeft, e.clientY - canvas.offsetTop];
}

/*
True if point is further from the canvas edges by POINT_RADIUS on each axis.
*/
function point_in_bounds(point) {
  return point[0] > POINT_RADIUS && point[0] < canvas.width - POINT_RADIUS &&
         point[1] > POINT_RADIUS && point[1] < canvas.height - POINT_RADIUS;
}

/*
If point is in site_points, return it.
Otherwise return undefined.
*/
function find_point(point) {
  return site_points.find(function(p) {
    if (p[0] === point[0] && p[1] === point[1]) {
      return true;
    }
    return false;
  });
}

/*
If point is nearby (within POINT_RADIUS) of some point in site_points, return the index of that point.
Otherwise return undefined.
*/
function find_nearby_point(point) {
  var idx = site_points.findIndex(function(p) {
    return point_inside_expanded_point(point, p);
  });
  return (idx === -1) ? undefined: idx;
}

/*
Expand expanded_point into a circle with POINT_RADIUS
and check if the given point is inside.
*/
function point_inside_expanded_point(point, expanded_point) {
  return Math.pow(point[0] - expanded_point[0], 2) + Math.pow(point[1] - expanded_point[1], 2) < Math.pow(POINT_RADIUS, 2);
}

