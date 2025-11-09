import org.junit.jupiter.api.Test;
import vanisimov.graphdevkit.graphelements.Vertex;
import vanisimov.graphdevkit.graphimplementings.AdListGraph;
import vanisimov.graphdevkit.graphimplementings.AdMatrixGraph;
import vanisimov.graphdevkit.graphimplementings.IncMatrixGraph;
import vanisimov.graphdevkit.graphint.Graph;

public class ToStringTest {

    @Test
    void toStringAdjList() {
        AdListGraph graph = new AdListGraph();

        graph.addVertex("A", new Vertex(0));
        graph.addVertex("B", new Vertex(0));
        graph.addVertex("C", new Vertex(0));

        graph.addEdge("A", "B");

        String actual = graph.toString();

        assert (actual.contains("A:\t[B]\t\n"));

        assert (actual.contains("B:\t\n"));

        assert (actual.contains("C:\t\n"));

    }

    @Test
    void toStringIncMatrix() {
        IncMatrixGraph graph = new IncMatrixGraph();

        graph.addVertex("A", new Vertex(0));
        graph.addVertex("B", new Vertex(0));
        graph.addVertex("C", new Vertex(0));

        graph.addEdge("A", "B");

        String actual = graph.toString();

        assert (actual.contains("\t\tA\tB\tC\t"));

        assert (actual.contains("A->B_0\t"));

        assert (actual.contains("\t1\t-1\t0\t") ||
                actual.contains("\t1\t0\t-1\t") ||
                actual.contains("\t-1\t1\t0\t"));
    }

    @Test
    void toStringAdjMatrix() {
        Graph graph = new AdMatrixGraph();

        graph.addVertex("A", new Vertex(0));
        graph.addVertex("B", new Vertex(0));
        graph.addVertex("C", new Vertex(0));

        graph.addEdge("A", "B");

        String actual = graph.toString();

        assert (actual.contains("\tA\tB\tC\t"));

        assert (actual.contains("A |\t0\t1\t0\t"));

        assert (actual.contains("B |\t0\t0\t0\t"));

        assert (actual.contains("C |\t0\t0\t0\t"));
    }

}
