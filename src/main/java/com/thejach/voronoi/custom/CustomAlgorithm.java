package com.thejach.voronoi.custom;

import com.thejach.voronoi.VoronoiAlgorithm;
import com.thejach.voronoi.datastructures.BoundingBox;
import com.thejach.voronoi.datastructures.Edge;
import com.thejach.voronoi.datastructures.Graph;
import com.thejach.voronoi.datastructures.SitePoints;

public class CustomAlgorithm implements VoronoiAlgorithm {

    private SitePoints points;
    private BoundingBox boundingBox;
    private Graph graph;

    /**
     * @param points - structure of equal length xs and ys with at least 2 items in each. (xs[i], ys[i]) form a site point.
     * @param boundingBox - the plane is bounded by a box defined by the two diagonal points (minX, minY) and (maxX, maxY).
     */
    public CustomAlgorithm(SitePoints points, BoundingBox boundingBox) {
        this.points = points;
        this.boundingBox = boundingBox;
    }

    @Override
    public void generate() {
        // The graph represents a set of edges to be drawn or processed.
        graph = new Graph();

        // Extract out the first two points (you are always guaranteed at least two points)
        double x0 = points.xs[0];
        double y0 = points.ys[0];
        double x1 = points.xs[1];
        double y1 = points.ys[1];

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
        //     inputs are m, the line slope, and some known point (a, b) on the line


        // The following code is for demoing how to use the available data and APIs and should be removed/commented out
        // for your solution.
        // You can add whatever other methods and classes you need,
        // but ultimately you need to add edges to the graph to get things to draw.

        // "addEdge" is how you make line segments get drawn by the UI.
        // It takes four arguments -- the first two define the starting (x,y) point of the line segment,
        // and the last two define the ending (x,y) point of the line segment.
        // Here we draw two line segments from the corners of the draw screen (bounding box) to form an 'X'.
        graph.addEdge(new Edge(boundingBox.minX, boundingBox.minY,
                boundingBox.maxX, boundingBox.maxY));
        graph.addEdge(new Edge(boundingBox.minX, boundingBox.maxY,
                boundingBox.maxX, boundingBox.minY));

        // Here we draw an edge that connects the first two points.
        graph.addEdge(new Edge(x0, y0,
                x1, y1));

        // Note that you can also draw points, not just line segments, by making an 'edge'
        // with the same start point and end point. Here's a point drawn
        // at the middle of the screen, down 5 pixels.
        graph.addEdge(new Edge(boundingBox.maxX/2, boundingBox.maxY/2-5, boundingBox.maxX/2, boundingBox.maxY/2-5));
        // Thinking in terms of which points should be drawn may be helpful if thinking in terms of
        // line segments isn't working...



    }

    @Override
    public Graph getGraph() {
        return graph;
    }
}
