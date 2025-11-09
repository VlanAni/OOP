import org.junit.jupiter.api.Test;
import vanisimov.graphdevkit.graphelements.Vertex;
import vanisimov.graphdevkit.graphimplementings.AdListGraph;
import vanisimov.graphdevkit.graphimplementings.AdMatrixGraph;
import vanisimov.graphdevkit.graphimplementings.IncMatrixGraph;
import vanisimov.graphdevkit.graphint.Graph;

import java.util.ArrayList;

public class SortTest {

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

}
