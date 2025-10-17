package vanisimov.graphdevkit.graphimplementings;

import vanisimov.graphdevkit.graphelements.*;
import vanisimov.graphdevkit.graphint.GraphInterface;
import java.util.ArrayList;

abstract public class Graph implements GraphInterface {

    public abstract void addVertex(Vertex vertex);

    public abstract void deleteVertex(int vertexIdx);

    public abstract void addEdge(Edge edge);

    public abstract void deleteEdge(int edgeIdx);

    public abstract ArrayList<Vertex> getNeibs(int vertexIdx);

    public abstract Graph readFile(String path);

    public abstract void printGraph();

    public abstract void sort();

    public abstract int vertexVal(int vertexIdx);

    public abstract void setvertexVal(int vertexIdx);

}