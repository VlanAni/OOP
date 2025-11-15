package vanisimov.graphdevkit.graphimplementings;

import java.util.*;
import vanisimov.graphdevkit.graphelements.Vertex;
import vanisimov.graphdevkit.graphelements.Edge;
import vanisimov.graphdevkit.graphint.Graph;

public class IncMatrixGraph implements Graph {

    private HashMap<String, Vertex> vertexes;
    private HashMap<String, Edge> edges;
    private HashMap<String, HashMap<String, Integer>> incMatrix;
    private int edgeCounter;

    public IncMatrixGraph() {
        this.vertexes = new HashMap<String, Vertex>();
        this.edges = new HashMap<String, Edge>();
        this.incMatrix = new HashMap<String, HashMap<String, Integer>>();
        this.edgeCounter = 0;
    }

    public void addVertex(String name, Vertex vertex) {
        if (this.vertexes.get(name) != null) {
            return;
        } else {
            this.vertexes.put(name, vertex);
            for (String edgeName : this.edges.keySet()) {
                this.incMatrix.get(edgeName).put(name, 0);
            }
        }
    }

    public void deleteVertex(String name) {
        if (this.vertexes.get(name) == null) {
            return;
        }

        for (String edgeName : this.incMatrix.keySet()) {
            this.incMatrix.get(edgeName).remove(name);
        }

        ArrayList<String> edgesToRemove = new ArrayList<String>();
        for (String edgeName : this.edges.keySet()) {
            Edge edge = this.edges.get(edgeName);
            String[] vertexes = edge.getVers();
            if (vertexes[0].equals(name) || vertexes[1].equals(name)) {
                edgesToRemove.add(edgeName);
            }
        }

        for (String edgeName : edgesToRemove) {
            this.incMatrix.remove(edgeName);
            this.edges.remove(edgeName);
        }

        this.vertexes.remove(name);
    }

    public void addEdge(String src, String dst) {
        if (this.vertexes.get(src) == null || this.vertexes.get(dst) == null) {
            return;
        } else {
            String edgeName = src + "->" + dst + "_" + this.edgeCounter;
            this.edgeCounter++;
            Edge edge = new Edge(src, dst);
            this.edges.put(edgeName, edge);

            HashMap<String, Integer> edgeRow = new HashMap<String, Integer>();
            for (String vertexName : this.vertexes.keySet()) {
                if (vertexName.equals(src)) {
                    edgeRow.put(vertexName, 1);
                } else if (vertexName.equals(dst)) {
                    edgeRow.put(vertexName, -1);
                } else {
                    edgeRow.put(vertexName, 0);
                }
            }
            this.incMatrix.put(edgeName, edgeRow);
        }
    }

    public void deleteEdge(String src, String dst) {
        if (this.vertexes.get(src) == null || this.vertexes.get(dst) == null) {
            return;
        }

        String prefix = src + "->" + dst + "_";
        String edgeName = null;
        for (String edge : this.edges.keySet()) {
            if (edge.startsWith(prefix)) {
                edgeName = edge;
                break;
            }
        }

        if (edgeName != null) {
            this.edges.remove(edgeName);
            this.incMatrix.remove(edgeName);
        }
    }

    public ArrayList<String> getNeibs(String name) {
        ArrayList<String> nbs = new ArrayList<String>();

        if (this.vertexes.get(name) == null) {
            return nbs;
        }

        for (String edgeName : this.incMatrix.keySet()) {
            HashMap<String, Integer> edgeRow = this.incMatrix.get(edgeName);
            if (edgeRow.get(name) == 1) {
                boolean isLoop = true;
                for (String vertexName : edgeRow.keySet()) {
                    if (edgeRow.get(vertexName) == -1) {
                        isLoop = false;
                        nbs.add(vertexName);
                    }
                }
                if (isLoop) {
                    nbs.add(name);
                }
            }
        }
        return nbs;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\t\t");
        String[] verts = this.vertexes.keySet().toArray(new String[0]);
        for (String v : verts) {
            str.append(v).append('\t');
        }
        str.append('\n');
        for (String edgeName : this.incMatrix.keySet()) {
            str.append(edgeName).append('\t');
            HashMap<String, Integer> edgeRow = this.incMatrix.get(edgeName);
            for (String vertex : verts) {
                str.append(edgeRow.get(vertex)).append('\t');
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
        String[] edgesVers;
        String edgeIdx;
        for (Edge edge : this.edges.values()) {
            edgesVers = edge.getVers();
            edgeIdx = edgesVers[0] + "->" + edgesVers[1];
            if (edges.containsKey(edgeIdx)) {
                int value = edges.get(edgeIdx);
                value++;
                edges.put(edgeIdx, value);
            } else {
                edges.put(edgeIdx, 1);
            }
        }
        return edges;
    }
}