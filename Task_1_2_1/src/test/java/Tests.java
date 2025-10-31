import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import vanisimov.graphdevkit.graphelements.Vertex;
import vanisimov.graphdevkit.graphimplementings.AdListGraph;
import vanisimov.graphdevkit.graphimplementings.AdMatrixGraph;
import vanisimov.graphdevkit.graphimplementings.Graph;
import vanisimov.graphdevkit.graphimplementings.IncMatrixGraph;
import vanisimov.graphdevkit.graphint.GraphInterface;
import vanisimov.graphdevkit.io.Out;

class Tests {

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
    void tsortadjmTest() {
        Graph graph = new AdMatrixGraph();
        graph.addVertex("A", new Vertex(1));
        graph.addVertex("B", new Vertex(1));
        graph.addVertex("C", new Vertex(1));
        graph.addVertex("D", new Vertex(1));

        graph.addEdge("A", "C");
        graph.addEdge("A", "D");
        graph.addEdge("B", "C");
        graph.addEdge("B", "D");

        ArrayList<String> result = graph.sort();

        assert(result != null);

        assert(result.indexOf("A") < result.indexOf("C"));

        assert(result.indexOf("A") < result.indexOf("D"));

        assert(result.indexOf("B") < result.indexOf("C"));

        assert(result.indexOf("B") < result.indexOf("D"));
    }

    @Test
    void incmatrixTest1() {
        Graph graph = new IncMatrixGraph();
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
    void tsortincmTest() {
        Graph graph = new IncMatrixGraph();
        graph.addVertex("A", new Vertex(1));
        graph.addVertex("B", new Vertex(1));
        graph.addVertex("C", new Vertex(1));
        graph.addVertex("D", new Vertex(1));

        graph.addEdge("A", "C");
        graph.addEdge("A", "D");
        graph.addEdge("B", "C");
        graph.addEdge("B", "D");

        ArrayList<String> result = graph.sort();

        assert(result != null);

        assert(result.indexOf("A") < result.indexOf("C"));

        assert(result.indexOf("A") < result.indexOf("D"));

        assert(result.indexOf("B") < result.indexOf("C"));

        assert(result.indexOf("B") < result.indexOf("D"));
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
    void tsortadjlTest() {
        Graph graph = new AdListGraph();
        graph.addVertex("A", new Vertex(1));
        graph.addVertex("B", new Vertex(1));
        graph.addVertex("C", new Vertex(1));
        graph.addVertex("D", new Vertex(1));

        graph.addEdge("A", "C");
        graph.addEdge("A", "D");
        graph.addEdge("B", "C");
        graph.addEdge("B", "D");

        ArrayList<String> result = graph.sort();

        assert(result != null);

        assert(result.indexOf("A") < result.indexOf("C"));

        assert(result.indexOf("A") < result.indexOf("D"));

        assert(result.indexOf("B") < result.indexOf("C"));

        assert(result.indexOf("B") < result.indexOf("D"));
    }

    @Test
    void fileReading1() {
        String filepath = "test1.txt";
        URL resource = getClass().getClassLoader().getResource(filepath);
        try {
            filepath = Paths.get(resource.toURI()).toAbsolutePath().toString();
            Graph graph = GraphInterface.readFile(filepath);
            ArrayList<String> nbs = graph.getNeibs("a");

            assert (nbs.isEmpty());

            nbs = graph.getNeibs("b");

            assert (!nbs.contains("b") && nbs.contains("a"));

            nbs = graph.getNeibs("c");

            assert (nbs.contains("b") && nbs.contains("a"));

            ArrayList<String> topoSort = graph.sort();

            assert (topoSort.indexOf("c") == 0 &&
                    topoSort.indexOf("b") == 1 &&
                    topoSort.indexOf("a") == 2);

        } catch (URISyntaxException e) {
            Out.println("Error with URI");
        }
    }

    @Test
    void fileReading2() {
        String filepath = "test2.txt";
        URL resource = getClass().getClassLoader().getResource(filepath);
        try {
            filepath = Paths.get(resource.toURI()).toAbsolutePath().toString();
            Graph graph = GraphInterface.readFile(filepath);

            assert (graph == null);

        } catch (URISyntaxException e) {
            Out.println("Error with URI");
        }
    }

    @Test
    void fileReading3() {
        String filepath = "test3.txt";
        URL resource = getClass().getClassLoader().getResource(filepath);
        try {
            filepath = Paths.get(resource.toURI()).toAbsolutePath().toString();
            Graph graph = GraphInterface.readFile(filepath);
            ArrayList<String> nbs = graph.getNeibs("a");

            assert (nbs.isEmpty());

            nbs = graph.getNeibs("b");

            assert (nbs.isEmpty());

            nbs = graph.getNeibs("c");

            assert (nbs.isEmpty());

            nbs = graph.getNeibs("d");

            assert (nbs.isEmpty());

            nbs = graph.getNeibs("e");

            assert (nbs.isEmpty());

        } catch (URISyntaxException e) {
            Out.println("Error with URI");
        }
    }

    @Test
    void fileReading4() {
        String filepath = "test4.txt";
        URL resource = getClass().getClassLoader().getResource(filepath);
        try {
            filepath = Paths.get(resource.toURI()).toAbsolutePath().toString();
            Graph graph = GraphInterface.readFile(filepath);

            assert (graph != null);

        } catch (URISyntaxException e) {
            Out.println("Error with URI");
        }
    }
}