package vanisimov.graphdevkit.graphint;

import vanisimov.graphdevkit.graphelements.*;
import vanisimov.graphdevkit.graphimplementings.Graph;
import java.util.ArrayList;

public interface GraphInterface {

    void addVertex(String name, Vertex vertex);

    void deleteVertex(String name);

    void addEdge(String src, String dst);

    void deleteEdge(String src, String dst);

    ArrayList<String> getNeibs(String name);

    Graph readFile(String path);

    void printGraph();

    ArrayList<String> sort();

    int vertexVal(String name);

    void setvertexVal(String name, int val);

}