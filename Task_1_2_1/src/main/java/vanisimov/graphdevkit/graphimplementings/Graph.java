package vanisimov.graphdevkit.graphimplementings;

import vanisimov.graphdevkit.graphelements.*;
import vanisimov.graphdevkit.graphint.GraphInterface;
import java.util.ArrayList;

abstract public class Graph implements GraphInterface {

    public abstract void addVertex(String name, Vertex vertex);

    public abstract void deleteVertex(String name);

    public abstract void addEdge(String src, String dst);

    public abstract void deleteEdge(String src, String dst);

    public abstract ArrayList<String> getNeibs(String name);

    public abstract Graph readFile(String path);

    public abstract void printGraph();

    public abstract ArrayList<String> sort();

    public abstract int vertexVal(String name);

    public abstract void setvertexVal(String name, int val);

}