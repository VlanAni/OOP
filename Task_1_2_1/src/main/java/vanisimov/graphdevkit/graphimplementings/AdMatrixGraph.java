package vanisimov.graphdevkit.graphimplementings;

import vanisimov.graphdevkit.graphelements.*;
import vanisimov.graphdevkit.io.Out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AdMatrixGraph extends Graph {

    private HashMap<String, Vertex> vertexes;
    private HashMap<String, HashMap<String, Integer>> adjMatrix;

    public AdMatrixGraph() {
        this.vertexes = new HashMap<String, Vertex>();
        this.adjMatrix = new HashMap<String, HashMap<String, Integer>>();
    }

    /**
     * Add the vertex with the name [name] to graph if the graph doesn't contain it.
     *
     * @param name - name for the vertex.
     * @param vertex - object of the vertex.
     */
    @Override
    public void addVertex(String name, Vertex vertex) {
        if (this.vertexes.get(name) != null) {
            return;
        } else {
            this.vertexes.put(name, vertex);
            this.adjMatrix.put(name, new HashMap<String, Integer>());
            for (String ver : this.vertexes.keySet()) {
                this.adjMatrix.get(name).put(ver, 0);
                this.adjMatrix.get(ver).put(name, 0);
            }
        }
    }

    @Override
    public void deleteVertex(String name) {
        if (this.vertexes.get(name) == null || this.adjMatrix.get(name) == null) {
            return;
        }
        for (String ver : this.vertexes.keySet()) {
            this.adjMatrix.get(name).put(ver, 0);
            this.adjMatrix.get(ver).put(name, 0);
        }
        this.vertexes.remove(name, this.vertexes.get(name));
        this.adjMatrix.remove(name, this.adjMatrix.get(name));
    }

    /**
     * Add edge if dst and src nodes exist in this graph.
     *
     * @param dst - first node
     * @param src - second node
     */
    @Override
    public void addEdge(String src, String dst) {
        if (this.vertexes.get(dst) == null || this.vertexes.get(src) == null) {
            return;
        } else {
            int value = this.adjMatrix.get(src).get(dst);
            value++;
            this.adjMatrix.get(src).put(dst, value);
        }
    }

    /**
     * Delete edge if it exists.
     *
     * @param dst - first node
     * @param src - second node
     */
    @Override
    public void deleteEdge(String src, String dst) {
        if (this.vertexes.get(dst) == null || this.vertexes.get(src) == null) {
            return;
        } else {
            int value = this.adjMatrix.get(src).get(dst);
            if (value > 0) {
                value--;
                this.adjMatrix.get(src).put(dst, value);
            }
        }
    }

    @Override
    public ArrayList<String> getNeibs(String name) {
        ArrayList<String> nbrs = new ArrayList<String>();

        if (this.vertexes.get(name) == null) {
            return nbrs;
        }

        for (String vertex : this.vertexes.keySet()) {
            if (this.adjMatrix.get(name).get(vertex) > 0) {
                nbrs.add(vertex);
            }
        }
        return nbrs;
    }

    @Override
    public void printGraph() {
        Out.print("\t");
        String[] verts = this.vertexes.keySet().toArray(new String[0]);
        for (String v : verts) {
            Out.printf("%s\t", v);
        }
        Out.print("\n");
        HashMap<String, Integer> array;
        for (String v : verts) {
            Out.printf("%s |\t", v);
            array = this.adjMatrix.get(v);
            for (String adj : verts) {
                Out.printf("%d\t", array.get(adj));
            }
            Out.print("\n");
        }
    }

    @Override
    public ArrayList<String> sort() {
        ArrayList<String> result = new ArrayList<>();
        HashMap<String, Integer> visited = new HashMap<>(); // 0 - не посещена, 1 - посещена, 2 - обработана
        for (String vertex : vertexes.keySet()) {
            visited.put(vertex, 0);
        }
        for (String vertex : vertexes.keySet()) {
            if (visited.get(vertex) == 0) {
                if (!DFS(vertex, visited, result)) {
                    return null;
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

    private boolean DFS(String curr, HashMap<String, Integer> visited, ArrayList<String> result) {
        visited.put(curr, 1);
        for (String neighbor : getNeibs(curr)) {
            if (visited.get(neighbor) == 1) {
                return false; // найден цикл
            }
            if (visited.get(neighbor) == 0) {
                if (!DFS(neighbor, visited, result)) {
                    return false;
                }
            }
        }
        visited.put(curr, 2);
        result.add(curr);
        return true;
    }

    @Override
    public int vertexVal(String name) {
        if (this.vertexes.get(name) == null) {
            return -1;
        }
        return this.vertexes.get(name).getValue();
    }

    @Override
    public void setvertexVal(String name, int val) {
        if (this.vertexes.get(name) == null) {
            return;
        }
        this.vertexes.get(name).setValue(val);
    }
}