package vanisimov.graphdevkit.graphimplementings;

import vanisimov.graphdevkit.graphelements.Vertex;
import vanisimov.graphdevkit.graphint.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class AdListGraph implements Graph {

    private HashMap<String, Vertex> vertexes;
    private HashMap<String, ArrayList<String>> adjLists;

    public AdListGraph() {
        this.vertexes = new HashMap<String, Vertex>();
        this.adjLists = new HashMap<String, ArrayList<String>>();
    }

    public void addVertex(String name, Vertex vertex) {
        if (this.vertexes.get(name) != null) {
            return;
        } else {
            this.vertexes.put(name, vertex);
            this.adjLists.put(name, new ArrayList<String>());
        }
    }

    public void deleteVertex(String name) {
        if (this.vertexes.get(name) == null) {
            return;
        }

        for (String vertexName : this.vertexes.keySet()) {
            ArrayList<String> neighbors = this.adjLists.get(vertexName);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                if (neighbors.get(i).equals(name)) {
                    neighbors.remove(i);
                }
            }
        }

        this.vertexes.remove(name);
        this.adjLists.remove(name);
    }

    public void addEdge(String src, String dst) {
        if (this.vertexes.get(src) == null || this.vertexes.get(dst) == null) {
            return;
        } else {
            ArrayList<String> nbs = this.adjLists.get(src);
            nbs.add(dst);
        }
    }

    public void deleteEdge(String src, String dst) {
        if (this.vertexes.get(src) == null || this.vertexes.get(dst) == null) {
            return;
        } else {
            ArrayList<String> nbs = this.adjLists.get(src);
            for (int i = 0; i < nbs.size(); i++) {
                if (nbs.get(i).equals(dst)) {
                    nbs.remove(i);
                    break;
                }
            }
        }
    }

    public ArrayList<String> getNeibs(String name) {
        ArrayList<String> neighbors = new ArrayList<String>();
        if (this.vertexes.get(name) == null) {
            return neighbors;
        }

        ArrayList<String> allNeighbors = this.adjLists.get(name);
        for (String neighbor : allNeighbors) {
            if (!neighbors.contains(neighbor)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String v : this.vertexes.keySet()) {
            str.append(v).append(":\t");
            for (String adjVertex : this.adjLists.get(v)) {
                str.append("[").append(adjVertex).append("]\t");
            }
            str.append('\n');
        }
        return str.toString();
    }

    public ArrayList<String> sort() {
        ArrayList<String> result = new ArrayList<>();
        HashMap<String, Integer> visited = new HashMap<>();
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
                return false;
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

    public int vertexVal(String name) {
        if (this.vertexes.get(name) == null) {
            return -1;
        }
        return this.vertexes.get(name).getValue();
    }

    public void setvertexVal(String name, int val) {
        if (this.vertexes.get(name) == null) {
            return;
        }
        this.vertexes.get(name).setValue(val);
    }

    public HashSet<String> getVertexes() {
        HashSet<String> vertexes = new HashSet<String>();
        vertexes.addAll(this.vertexes.keySet());
        return vertexes;
    }

    public HashMap<String, Integer> getEdges() {
        HashMap<String, Integer> edges = new HashMap<String, Integer>();
        ArrayList<String> neighbors = null;
        String edgeIdx = null;
        for (String src : this.vertexes.keySet()) {
            neighbors = this.adjLists.get(src);
            for (String dst : neighbors) {
                edgeIdx = src + "->" + dst;
                if (edges.containsKey(edgeIdx)) {
                    int value = edges.get(edgeIdx);
                    value++;
                    edges.put(edgeIdx, value);
                } else {
                    edges.put(edgeIdx, 1);
                }
            }
        }
        return edges;
    }
}