package vanisimov.graphdevkit.graphint;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import vanisimov.graphdevkit.graphelements.*;
import vanisimov.graphdevkit.graphimplementings.AdListGraph;
import vanisimov.graphdevkit.graphimplementings.AdMatrixGraph;
import vanisimov.graphdevkit.graphimplementings.IncMatrixGraph;
import vanisimov.graphdevkit.io.*;

public interface Graph {

    void addVertex(String name, Vertex vertex);

    void deleteVertex(String name);

    void addEdge(String src, String dst);

    void deleteEdge(String src, String dst);

    ArrayList<String> getNeibs(String name);

    static Graph readFile(String path) {
        try (In file = new In(path)) {

            Graph graph = createGraph(file);
            if (graph == null) {
                return null;
            }

            if (!readVertexes(file, graph)) {
                return null;
            }

            if (!readEdges(file, graph)) {
                return null;
            }

            return graph;

        } catch (FileNotFoundException e) {
            Out.println("File hasn't been found");
            return null;
        }
    }

    private static Graph createGraph(In file) {
        String graphType = file.readString();

        if (graphType == null) {
            Out.println("File has to include the graph type: Inc | AdList | AdMatrix");
            return null;
        }

        switch (graphType) {
            case "Inc":
                return new IncMatrixGraph();
            case "AdList":
                return new AdListGraph();
            case "AdMatrix":
                return new AdMatrixGraph();
            default:
                Out.println("File has to include the graph type: Inc | AdList | AdMatrix");
                return null;
        }
    }

    private static boolean readVertexes(In file, Graph graph) {
        String verAmountStr = file.readString();
        if (verAmountStr == null) {
            return true;
        }

        try {
            int verAmount = Integer.parseInt(verAmountStr);
            for (int i = 0; i < verAmount; ++i) {
                String verName = file.readString();
                if (verName == null) {
                    Out.println("There are less vertexes than defined");
                    return false;
                }
                graph.addVertex(verName, new Vertex(0));
            }
            return true;
        } catch (NumberFormatException e) {
            Out.println("Wrong number format for vertex amount");
            return false;
        }
    }

    private static boolean readEdges(In file, Graph graph) {
        String edgesAmountStr = file.readString();
        if (edgesAmountStr == null) {
            return true;
        }

        try {
            int edgesAmount = Integer.parseInt(edgesAmountStr);
            for (int i = 0; i < edgesAmount; ++i) {
                String edgeStr = file.readString();
                if (edgeStr == null) {
                    Out.println("There are less edges than defined");
                    return false;
                }

                String[] splitEdge = edgeStr.split(" ");

                if (splitEdge.length != 3 || !splitEdge[1].trim().equals("->")) {
                    Out.println("Wrong edge format! Right format: v1 -> v2");
                    return false;
                }

                String ver1 = splitEdge[0].trim();
                String ver2 = splitEdge[2].trim();

                if (ver1.isEmpty() || ver2.isEmpty()) {
                    Out.println("Wrong edge format! Vertex name cannot be empty.");
                    return false;
                }

                graph.addEdge(ver1, ver2);
            }
            return true;
        } catch (NumberFormatException e) {
            Out.println("Wrong number format for edge amount");
            return false;
        }
    }

    static boolean compareGraphs(Graph g1, Graph g2) {

        if (g1 == null || g2 == null) {
            return false;
        }

        HashSet<String> g1Vert = g1.getVertexes();
        HashSet<String> g2Vert = g2.getVertexes();

        if (!g1Vert.equals(g2Vert)) {
            return false;
        }

        HashMap<String, Integer> g1Edges = g1.getEdges();
        HashMap<String, Integer> g2Edges = g2.getEdges();

        return g1Edges.equals(g2Edges);
    }

    HashMap<String, Integer> getEdges();

    HashSet<String> getVertexes();

    ArrayList<String> sort();

    int vertexVal(String name);

    void setvertexVal(String name, int val);

}