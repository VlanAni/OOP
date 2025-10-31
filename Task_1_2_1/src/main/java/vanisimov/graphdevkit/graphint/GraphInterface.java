package vanisimov.graphdevkit.graphint;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import vanisimov.graphdevkit.graphelements.*;
import vanisimov.graphdevkit.graphimplementings.AdListGraph;
import vanisimov.graphdevkit.graphimplementings.AdMatrixGraph;
import vanisimov.graphdevkit.graphimplementings.Graph;
import vanisimov.graphdevkit.graphimplementings.IncMatrixGraph;
import vanisimov.graphdevkit.io.*;

public interface GraphInterface {

    void addVertex(String name, Vertex vertex);

    void deleteVertex(String name);

    void addEdge(String src, String dst);

    void deleteEdge(String src, String dst);

    ArrayList<String> getNeibs(String name);

    static Graph readFile(String path) {
        try (In file = new In(path)) {
            String graphType;
            graphType = file.readString();
            Graph graph;
            if (graphType == null) {
                Out.println("File has to include thr graph type: Inc | AdList | AdMatrix");
                return null;
            } else if (graphType.equals("Inc")) {
                graph = new IncMatrixGraph();
            } else if (graphType.equals("AdList")) {
                graph = new AdListGraph();
            } else if (graphType.equals("AdMatrix")) {
                graph = new AdMatrixGraph();
            } else {
                Out.println("File has to include the graph type: Inc | AdList | AdMatrix");
                return null;
            }
            String intStr = file.readString();
            if (intStr == null) {
                return graph; // return the empty graph
            } else {
                try {
                    int verAmount = Integer.parseInt(intStr);
                    String verName = "";
                    for (int i = 1; i <= verAmount; ++i) {
                        verName = file.readString();
                        if (verName == null) {
                            Out.println("There are less vertexes than defined");
                            return null;
                        } else {
                            graph.addVertex(verName, new Vertex(0));
                        }
                    }
                } catch (NumberFormatException e) {
                    Out.println("Wrong number format");
                    return null;
                }
            }
            intStr = file.readString();
            if (intStr == null) {
                return graph; // return the graph without any edges
            } else {
                try {
                    int edgesAmount = Integer.parseInt(intStr);
                    String edgeStr = "";
                    for (int i = 1; i <= edgesAmount; ++i) {
                        edgeStr = file.readString();
                        if (edgeStr == null) {
                            Out.println("There are less edges than defined");
                            return null;
                        } else {
                            String[] splitEdge = edgeStr.split(" ");
                            if (splitEdge.length != 3) {
                                Out.println("Wrong edge format! Right format: v1 -> v2");
                                return null;
                            }
                            String ver1 = splitEdge[0].trim();
                            String ver2 = splitEdge[2].trim();
                            if (ver1.isEmpty() || ver2.isEmpty() || !(splitEdge[1].equals("->"))) {
                                Out.println("Wrong edge format! Right format: v1 -> v2");
                                return null;
                            }
                            graph.addEdge(ver1, ver2);
                        }
                    }
                    return graph;
                } catch (NumberFormatException e) {
                    Out.println("Wrong number format");
                    return null;
                }
            }
        } catch (FileNotFoundException e) {
            Out.println("File hasn't been found");
            return null;
        }
    }

    void printGraph();

    ArrayList<String> sort();

    int vertexVal(String name);

    void setvertexVal(String name, int val);

}