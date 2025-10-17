package vanisimov.graphdevkit.graphint;

import vanisimov.graphdevkit.graphelements.*;
import vanisimov.graphdevkit.graphimplementings.Graph;
import java.util.ArrayList;

public interface GraphInterface {

    void addVertex(Vertex vertex);

    void deleteVertex(int vertexIdx);

    void addEdge(Edge edge);

    void deleteEdge(int edgeIdx);

    ArrayList<Vertex> getNeibs(int vertexIdx);

    Graph readFile(String path);

    void printGraph();

    void sort();

    int vertexVal(int vertexIdx);

    void setvertexVal(int vertexIdx);

}