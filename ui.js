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

// Pixels that points expand from to prevent duplicate slots (at 1) and too close neighbor points (at > 1)
// Points themselves may still be drawn at some size < this.
var POINT_RADIUS = 10;

var POINT_DRAW_RADIUS = 3;

// 'add' or 'remove' modes to change the canvas.
var point_mode = 'add';

// convenient canvas handle.
var canvas = null;

// List of [x, y] pairs for each added point.
var points = [];

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

  var add_points = document.getElementById('add_points');
  add_points.checked = true; // Reset in case of refresh
  add_points.addEventListener('click', on_click_add_mode);
  document.getElementById('remove_points').addEventListener('click', on_click_remove_mode);

  document.getElementById('clear_points').addEventListener('click', on_click_clear_points);

  document.getElementById('load_preset_1').addEventListener('click', on_click_load_preset_1);

  document.getElementById('run_custom').addEventListener('click', on_click_run_custom);

  document.getElementById('run_fortune').addEventListener('click', on_click_run_fortune);

  document.getElementById('fill_cells_with_color').addEventListener('click', on_click_fill_cells_with_color);

  redraw_canvas();
}

/*
When in add mode, records and draws the point, if the point doesn't intersect within POINT_RADIUS of an existing point.
When in remove mode, if there is a point that is clicked on that does intersect within POINT_RADIUS, remove that point.
*/
function on_click_canvas(e) {
  var point = canvas_pos(e);
  if (point_mode === 'add') {
    if (point_in_bounds(point) && // restrain points from being right up against the edge
        find_point(point) === undefined && // if point hasn't been added
        find_nearby_point(point) === undefined) { // and point isn't too close
          points.push(point);
          redraw_canvas();
    }
  } else if (point_mode === 'remove') {
    var nearby_point = find_nearby_point(point);
    if (nearby_point !== undefined) {
      points.splice(nearby_point, 1);
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

function on_click_clear_points(e) {
  points = [];
  redraw_canvas();
}

function on_click_load_preset_1(e) {
  points = [[300,150], [500,150], [400,450]];
  redraw_canvas();
}

function on_click_run_custom(e) {
  e.target.disabled = true;
  request_generate('custom', function(data) {
    e.target.disabled = false;
    console.log(data);
  });
}

function on_click_run_fortune(e) {
  e.target.disabled = true;
  request_generate('fortune', function(data) {
    e.target.disabled = false;
    draw_voronoi(data, 'fortune');
  });

}

function on_click_fill_cells_with_color(e) {
  // TODO
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
Redraws canvas with only the points in the points array drawn.
*/
function redraw_canvas() {
  var ctx = canvas.getContext('2d');

  ctx.fillStyle = '#ffffff';
  ctx.fillRect(0, 0, canvas.width, canvas.height);

  ctx.fillStyle = '#000000';

  for (var i = 0; i < points.length; i++) {
    var point = points[i];
    ctx.beginPath();
    ctx.arc(point[0], point[1], POINT_DRAW_RADIUS, 0, Math.PI * 2, 1);
    ctx.fill();
  }

}

function draw_voronoi(graph, algo) {
  var ctx = canvas.getContext('2d');

  if (algo === 'fortune') {
    // graph data is an array of {x1: x1, y1: y1, x2: x2, y2: y2} edges
    ctx.beginPath();

    for (var i = 0; i < graph.length; i++) {
      var edge = graph[i];
      ctx.moveTo(edge.x1, edge.y1);
      ctx.lineTo(edge.x2, edge.y2);
    }
    ctx.stroke();
  }
}

function request_generate(algorithm, cb) {
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
    points: points,
    bounding_box: [0, 0, canvas.width, canvas.height]
  }));
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
If point is in points, return it.
Otherwise return undefined.
*/
function find_point(point) {
  return points.find(function(p) {
    if (p[0] === point[0] && p[1] === point[1]) {
      return true;
    }
    return false;
  });
}

/*
If point is nearby (within POINT_RADIUS) of some point in points, return the index of that point.
Otherwise return undefined.
*/
function find_nearby_point(point) {
  var idx = points.findIndex(function(p) {
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

