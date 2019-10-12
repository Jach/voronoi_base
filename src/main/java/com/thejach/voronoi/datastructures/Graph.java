package com.thejach.voronoi.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Edge> edges;

    public Graph() {
        edges = new ArrayList<>();
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }
}
