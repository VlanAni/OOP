import org.junit.jupiter.api.Test;
import vanisimov.graphdevkit.graphelements.Vertex;
import vanisimov.graphdevkit.graphimplementings.AdListGraph;
import vanisimov.graphdevkit.graphimplementings.AdMatrixGraph;
import vanisimov.graphdevkit.graphimplementings.IncMatrixGraph;
import vanisimov.graphdevkit.graphint.Graph;

public class EqualsTest {

    private void createGraph(Graph graph) {
        graph.addVertex("A", new Vertex(0));
        graph.addVertex("B", new Vertex(0));
        graph.addVertex("C", new Vertex(0));

        graph.addEdge("A", "B");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
    }

    @Test
    void AdListIncMatrixEqual() {
        Graph g1 = new AdListGraph();
        createGraph(g1);

        Graph g2 = new IncMatrixGraph();
        createGraph(g2);

        assert (Graph.compareGraphs(g1, g2));
    }

    @Test
    void AdMatrixIncMatrixNotEqual() {

        Graph g1 = new AdMatrixGraph();
        createGraph(g1);

        Graph g2 = new IncMatrixGraph();
        g2.addVertex("A", new Vertex(0));
        g2.addVertex("B", new Vertex(0));
        g2.addVertex("C", new Vertex(0));
        g2.addEdge("A", "B");
        g2.addEdge("A", "B");
        g2.addEdge("A", "B");
        g2.addEdge("B", "C");


        assert (!Graph.compareGraphs(g1, g2));
    }

    @Test
    void AdListAdMatrixNotEqual() {

        Graph g1 = new AdListGraph();
        createGraph(g1);

        Graph g2 = new AdMatrixGraph();
        g2.addVertex("A", new Vertex(0));
        g2.addVertex("B", new Vertex(0));
        g2.addVertex("D", new Vertex(0));

        assert (!Graph.compareGraphs(g1, g2));
    }

}
