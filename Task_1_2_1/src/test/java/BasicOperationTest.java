import org.junit.jupiter.api.Test;
import vanisimov.graphdevkit.graphelements.Vertex;
import vanisimov.graphdevkit.graphimplementings.AdListGraph;
import vanisimov.graphdevkit.graphimplementings.AdMatrixGraph;
import vanisimov.graphdevkit.graphimplementings.IncMatrixGraph;
import vanisimov.graphdevkit.graphint.Graph;

import java.util.ArrayList;

public class BasicOperationTest {

    @Test
    void vertexTest() {
        Graph graph = new IncMatrixGraph();

        graph.addVertex("a", new Vertex(0));

        assert (graph.vertexVal("a") == 0);

        graph.setvertexVal("a", -5);

        assert (graph.vertexVal("a") == -5);
    }

    @Test
    void adjmatrixTest1() {
        Graph graph = new AdMatrixGraph();
        // добавили две вершины, петлю (a, a) и ребро (a, b)
        graph.addVertex("a", new Vertex(123));
        graph.addVertex("b", new Vertex(234));
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");

        ArrayList<String> nbrs = graph.getNeibs("a");

        assert (nbrs.contains("a") && nbrs.contains("b"));

        nbrs = graph.getNeibs("b");

        assert (nbrs.isEmpty());
    }

    @Test
    void adjmatrixTest2() {
        // создаём граф с четырьмя вершинами
        Graph graph = new AdMatrixGraph();
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("b", new Vertex(1));
        graph.addVertex("c", new Vertex(1));
        graph.addVertex("d", new Vertex(1));

        ArrayList<String> nbrs = graph.getNeibs("a");

        assert (nbrs.isEmpty());

        nbrs = graph.getNeibs("b");

        assert (nbrs.isEmpty());

        nbrs = graph.getNeibs("c");

        assert (nbrs.isEmpty());

        nbrs = graph.getNeibs("d");

        assert (nbrs.isEmpty());

    }

    @Test
    void incmatrixTest1() {
        Graph graph = new IncMatrixGraph();
        // добавили две вершины, петлю (a, a) и ребро (a, b)
        graph.addVertex("a", new Vertex(123));
        graph.addVertex("b", new Vertex(234));
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");
        graph.addEdge("a", "a");

        ArrayList<String> nbrs = graph.getNeibs("a");

        assert (nbrs.contains("a") && nbrs.contains("b"));

        nbrs = graph.getNeibs("b");

        assert (nbrs.isEmpty());

    }

    @Test
    void incmatrixTest2() {
        // создаём граф с четырьмя вершинами
        Graph graph = new IncMatrixGraph();
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("b", new Vertex(1));
        graph.addVertex("c", new Vertex(1));
        graph.addVertex("d", new Vertex(1));

        // добавляем рёбра
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("a", "d");
        graph.addEdge("a", "a");
        graph.addEdge("b", "a");
        graph.addEdge("b", "a");
        graph.addEdge("c", "d");

        ArrayList<String> nbrs = graph.getNeibs("a");

        assert (nbrs.contains("a") &&
                nbrs.contains("b") &&
                nbrs.contains("c") &&
                nbrs.contains("d"));

        nbrs = graph.getNeibs("b");

        assert (nbrs.contains("a"));

        nbrs = graph.getNeibs("b");

        assert (nbrs.contains("a"));

        nbrs = graph.getNeibs("c");

        assert (nbrs.contains("d"));

        nbrs = graph.getNeibs("d");

        assert (nbrs.isEmpty());
    }

    @Test
    void checkingVertex() {
        Vertex vertex = new Vertex(2);
        assert (vertex.getValue() == 2);
        vertex.setValue(5);
        assert (vertex.getValue() == 5);
    }

    @Test
    void adjlistTest1() {
        Graph graph = new AdListGraph();
        // добавили две вершины, петлю (a, a) и ребро (a, b)
        graph.addVertex("a", new Vertex(123));
        graph.addVertex("b", new Vertex(234));
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");

        // удаляем одно ребро между (a, b)
        graph.deleteEdge("a", "b");
        graph.deleteEdge("b", "a");

        ArrayList<String> nbrs = graph.getNeibs("a");

        assert (nbrs.contains("a") && !nbrs.contains("b"));

        nbrs = graph.getNeibs("b");

        assert (nbrs.isEmpty());

    }

    @Test
    void adjlistTest2() {
        // создаём граф с четырьмя вершинами
        Graph graph = new AdListGraph();
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("b", new Vertex(1));
        graph.addVertex("c", new Vertex(1));
        graph.addVertex("d", new Vertex(1));

        // добавляем рёбра
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("a", "d");
        graph.addEdge("a", "a");
        graph.addEdge("b", "a");
        graph.addEdge("c", "d");

        ArrayList<String> nbrs = graph.getNeibs("a");

        assert (nbrs.contains("a") &&
                nbrs.contains("b") &&
                nbrs.contains("c") &&
                nbrs.contains("d"));

        nbrs = graph.getNeibs("b");

        assert (nbrs.contains("a"));

        nbrs = graph.getNeibs("b");

        assert (nbrs.contains("a"));

        nbrs = graph.getNeibs("c");

        assert (nbrs.contains("d"));

        nbrs = graph.getNeibs("d");

        assert (nbrs.isEmpty());

    }

    @Test
    void deleteVertexFromAdjMatrix() {

        Graph graph = new AdMatrixGraph();

        graph.addVertex("a", new Vertex(1));
        graph.addVertex("b", new Vertex(0));

        graph.addEdge("a", "b");
        graph.addEdge("b", "a");
        graph.deleteVertex("a");

        assert (graph.getNeibs("a").isEmpty());

        assert (graph.getNeibs("b").isEmpty());

    }

    @Test
    void deleteVertexFromAdjList() {

        Graph graph = new AdListGraph();

        graph.addVertex("a", new Vertex(1));
        graph.addVertex("b", new Vertex(0));

        graph.addEdge("a", "b");
        graph.addEdge("b", "a");
        graph.deleteVertex("a");

        assert (graph.getNeibs("a").isEmpty());

        assert (graph.getNeibs("b").isEmpty());

    }

    @Test
    void deleteVertexFromIncMatrix() {

        Graph graph = new IncMatrixGraph();

        graph.addVertex("a", new Vertex(1));
        graph.addVertex("b", new Vertex(0));

        graph.addEdge("a", "b");
        graph.addEdge("b", "a");
        graph.deleteVertex("a");

        assert (graph.getNeibs("a").isEmpty());

        assert (graph.getNeibs("b").isEmpty());

    }

}
