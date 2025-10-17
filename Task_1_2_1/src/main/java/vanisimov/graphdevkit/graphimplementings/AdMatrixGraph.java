package vanisimov.graphdevkit.graphimplementings;

import vanisimov.graphdevkit.customarray.DynamicArray;
import vanisimov.graphdevkit.graphelements.*;
import vanisimov.graphdevkit.io.Out;
import java.util.ArrayList;

public class AdMatrixGraph extends Graph {

    private ArrayList<Edge> edges;
    private ArrayList<Vertex> vertexes;
    private ArrayList<DynamicArray> adjMatrix;

    public AdMatrixGraph() {
        this.edges = new ArrayList<Edge>();
        this.vertexes = new ArrayList<Vertex>();
        this.adjMatrix = new ArrayList<DynamicArray>();
    }

    @Override
    public void addVertex(Vertex vertex) {
        this.vertexes.add(vertex);
        this.adjMatrix.add(new DynamicArray(this.vertexes.size()));
        if (this.vertexes.size() > 1) {
            for (int i = 0; i < this.adjMatrix.size() - 1; ++i) {
                this.adjMatrix.get(i).addElem(0);
            }
        }
    }

    @Override
    public void deleteVertex(int vertexIdx) {
        return;
    }

    @Override
    public void addEdge(Edge edge) {
        return;
    }

    @Override
    public void deleteEdge(int edgeIdx) {
        return;
    }

    @Override
    public ArrayList<Vertex> getNeibs(int vertexIdx) {
        return null;
    }

    @Override
    public Graph readFile(String path) {
        return null;
    }

    @Override
    public void printGraph() {
        Out.print("\t");
        for (int i = 0; i < this.adjMatrix.size(); ++i) {
            Out.printf("%d\t", i);
        }
        Out.print("\n");
        DynamicArray array;
        for (int i = 0; i < this.adjMatrix.size(); ++i) {
            Out.printf("%d |\t", i);
            array = this.adjMatrix.get(i);
            for (int j = 0; j < array.getSize(); ++j) {
                Out.printf("%d\t", array.getValue(j));
            }
            Out.print("\n");
        }
    }

    @Override
    public void sort() {
        return;
    }

    @Override
    public int vertexVal(int vertexIdx) {
        return 0;
    }

    @Override
    public void setvertexVal(int vertexIdx) {
        return;
    }

}