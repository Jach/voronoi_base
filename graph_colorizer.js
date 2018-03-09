/**
 * If you're reading this file maybe you're considering taking on the coloring extra credit?
 * Right now you might have noticed if you click on the "Request colorized cells" checkbox, you
 * are treated to a random coloring. That's generated below, and is ultimately what you have to output.
 * 
 * Colormap: a map between voronoi Site indexes and some hexadecimal color string.
 * 
 * Can you rewrite this so that no matter how many sites, the colormap is mapped such that no two adjacent
 * sites (sharing an edge) use the same color, and you use the minimum number of colors necessary?
 * 
 * The key input in the get_colormap function, edges, is the same edge list that you might output for the voronoi generation, with one twist,
 * if you're using the provided Fortune's Algorithm implementation.
 * Each edge contains two extra fields, "site1_i" and "site2_i", which are ids/indices referring to which two sites lie on each side
 * of the edge. From this information you can infer the connectivity of the graph.
 */

function get_colormap(edges, sites) {
  colormap = {};
  for (var i = 0; i < sites; i++) {
    colormap[i] = rand_color();
  }
  return colormap;
}

function rand_color() {
  var hexLetts = '0123456789abcdef';
  var color = '#';
  for (var i = 0; i < 6; i++) {
    color += hexLetts[Math.floor(Math.random()*hexLetts.length)];
  }
  return color;
}

