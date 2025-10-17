import org.junit.jupiter.api.Test;
import vanisimov.graphdevkit.customarray.DynamicArray;
import vanisimov.graphdevkit.graphelements.Vertex;
import vanisimov.graphdevkit.graphimplementings.AdMatrixGraph;
import vanisimov.graphdevkit.graphimplementings.Graph;

class Tests {
    @Test
    void daInit() {
        DynamicArray arr = new DynamicArray(0);
        arr.addElem(2);
        arr.addElem(3);
        arr.setValue(1, 5);

        assert(arr.getSize() == 2);
        assert(arr.getValue(0) == 2);
        assert(arr.getValue(1) == 5);
    }

    @Test
    void adjMatrix() {
        Graph graph = new AdMatrixGraph();
        graph.addVertex(new Vertex(123));
        graph.addVertex(new Vertex(234));
        graph.printGraph();
    }

    @Test
    void checkingVertex() {
        Vertex vertex = new Vertex(2);

        assert (vertex.getValue() == 2);

        vertex.setValue(5);

        assert (vertex.getValue() == 5);
    }
}