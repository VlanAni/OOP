import org.junit.jupiter.api.Test;
import vanisimov.graphdevkit.graphelements.Vertex;
import vanisimov.graphdevkit.graphimplementings.AdListGraph;
import vanisimov.graphdevkit.graphimplementings.AdMatrixGraph;
import vanisimov.graphdevkit.graphimplementings.Graph;
import vanisimov.graphdevkit.graphimplementings.IncMatrixGraph;
import vanisimov.graphdevkit.io.Out;

import java.util.ArrayList;

class Tests {

    @Test
    void adjmatrixTest1() {
        Out.println("==[DELETE/ADD] [EDGES/VERTEXES] [ADJACENT MATRIX]==");
        Graph graph = new AdMatrixGraph();
        // добавили две вершины, петлю (a, a) и ребро (a, b)
        graph.addVertex("a", new Vertex(123));
        graph.addVertex("b", new Vertex(234));
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");
        Out.println("==Graph №1==");
        graph.printGraph();

        // удаляем одно ребро между (a, b)
        graph.deleteEdge("a", "b");
        graph.deleteEdge("b", "a");
        Out.println("==Graph №2==");
        graph.printGraph();

        // удаляем вершину b
        graph.deleteVertex("b");
        Out.println("==Graph №3==");
        graph.printGraph();

        // удаляем ребро (a, a)
        graph.deleteEdge("a", "a");
        Out.println("==Graph №4==");
        graph.printGraph();

        // удаляем вершину a
        graph.deleteVertex("a");
        Out.println("==Graph №5==");
        graph.printGraph();

        // пытаемся удалить несуществующее ребро
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        Out.println("==Graph №6==");
        graph.printGraph();

        // добавляем вершину a, добавляем четыре петли
        graph.addVertex("a", new Vertex(123));
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        Out.println("==Graph №7==");
        graph.printGraph();
    }

    @Test
    void adjmatrixTest2() {
        Out.println("==[NEIGHBORS] [ADJACENT MATRIX]==");
        // создаём граф с четырьмя вершинами
        Graph graph = new AdMatrixGraph();
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("b", new Vertex(1));
        graph.addVertex("c", new Vertex(1));
        graph.addVertex("d", new Vertex(1));
        Out.println("==Graph №1==");
        graph.printGraph();

        // добавляем рёбра
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("a", "d");
        graph.addEdge("a", "a");
        graph.addEdge("b", "a");
        graph.addEdge("c", "d");
        Out.println("==Graph №2==");
        graph.printGraph();

        // теперь выводим соседей для каждой вершины
        Out.printf("==NEIGHBORS [a]==\n");
        ArrayList<String> nbrs = graph.getNeibs("a");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");

        Out.printf("==NEIGHBORS [b]==\n");
        nbrs = graph.getNeibs("b");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");

        Out.printf("==NEIGHBORS [c]==\n");
        nbrs = graph.getNeibs("c");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");

        Out.printf("==NEIGHBORS [d]==\n");
        nbrs = graph.getNeibs("d");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");
    }

    @Test
    void tsortadjmTest() {
        Out.println("==[TOPO SORT] [ADJACENT MATRIX]==");
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
        Out.println("==[DELETE/ADD] [EDGES/VERTEXES] [INCIDENCE MATRIX]==");
        Graph graph = new IncMatrixGraph();
        // добавили две вершины, петлю (a, a) и ребро (a, b)
        graph.addVertex("a", new Vertex(123));
        graph.addVertex("b", new Vertex(234));
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");
        Out.println("==Graph №1==");
        graph.printGraph();

        // удаляем одно ребро между (a, b)
        graph.deleteEdge("a", "b");
        Out.println("==Graph №2==");
        graph.printGraph();

        // удаляем вершину b
        graph.deleteVertex("b");
        Out.println("==Graph №3==");
        graph.printGraph();

        // удаляем ребро (a, a)
        graph.deleteEdge("a", "a");
        Out.println("==Graph №4==");
        graph.printGraph();

        // удаляем вершину a
        graph.deleteVertex("a");
        Out.println("==Graph №5==");
        graph.printGraph();

        // пытаемся добавить рёбра для несуществующих вершин
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        Out.println("==Graph №6==");
        graph.printGraph();

        // добавляем вершину a, добавляем четыре петли
        graph.addVertex("a", new Vertex(123));
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        Out.println("==Graph №7==");
        graph.printGraph();
    }

    @Test
    void incmatrixTest2() {
        Out.println("==[NEIGHBORS] [INCIDENCE MATRIX]==");
        // создаём граф с четырьмя вершинами
        Graph graph = new IncMatrixGraph();
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("b", new Vertex(1));
        graph.addVertex("c", new Vertex(1));
        graph.addVertex("d", new Vertex(1));
        Out.println("==Graph №1==");
        graph.printGraph();

        // добавляем рёбра
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("a", "d");
        graph.addEdge("a", "a");
        graph.addEdge("b", "a");
        graph.addEdge("c", "d");
        Out.println("==Graph №2==");
        graph.printGraph();

        // теперь выводим соседей для каждой вершины
        Out.printf("==NEIGHBORS [a]==\n");
        ArrayList<String> nbrs = graph.getNeibs("a");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");

        Out.printf("==NEIGHBORS [b]==\n");
        nbrs = graph.getNeibs("b");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");

        Out.printf("==NEIGHBORS [c]==\n");
        nbrs = graph.getNeibs("c");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");

        Out.printf("==NEIGHBORS [d]==\n");
        nbrs = graph.getNeibs("d");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");
    }

    @Test
    void tsortincmTest() {
        Out.println("==[TOPO SORT] [INCIDENCE MATRIX]==");
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
        Out.println("==[DELETE/ADD] [EDGES/VERTEXES] [ADJACENCY LIST]==");
        Graph graph = new AdListGraph();
        // добавили две вершины, петлю (a, a) и ребро (a, b)
        graph.addVertex("a", new Vertex(123));
        graph.addVertex("b", new Vertex(234));
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");
        Out.println("==Graph №1==");
        graph.printGraph();

        // удаляем одно ребро между (a, b)
        graph.deleteEdge("a", "b");
        graph.deleteEdge("b", "a");
        Out.println("==Graph №2==");
        graph.printGraph();

        // удаляем вершину b
        graph.deleteVertex("b");
        Out.println("==Graph №3==");
        graph.printGraph();

        // удаляем ребро (a, a)
        graph.deleteEdge("a", "a");
        Out.println("==Graph №4==");
        graph.printGraph();

        // удаляем вершину a
        graph.deleteVertex("a");
        Out.println("==Graph №5==");
        graph.printGraph();

        // пытаемся удалить несуществующее ребро
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        Out.println("==Graph №6==");
        graph.printGraph();

        // добавляем вершину a, добавляем четыре петли
        graph.addVertex("a", new Vertex(123));
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        graph.addEdge("a", "a");
        Out.println("==Graph №7==");
        graph.printGraph();
    }

    @Test
    void adjlistTest2() {
        Out.println("==[NEIGHBORS] [ADJACENCY LIST]==");
        // создаём граф с четырьмя вершинами
        Graph graph = new AdListGraph();
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("a", new Vertex(1));
        graph.addVertex("b", new Vertex(1));
        graph.addVertex("c", new Vertex(1));
        graph.addVertex("d", new Vertex(1));
        Out.println("==Graph №1==");
        graph.printGraph();

        // добавляем рёбра
        graph.addEdge("a", "a");
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("a", "d");
        graph.addEdge("a", "a");
        graph.addEdge("b", "a");
        graph.addEdge("c", "d");
        Out.println("==Graph №2==");
        graph.printGraph();

        // теперь выводим соседей для каждой вершины
        Out.printf("==NEIGHBORS [a]==\n");
        ArrayList<String> nbrs = graph.getNeibs("a");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");

        Out.printf("==NEIGHBORS [b]==\n");
        nbrs = graph.getNeibs("b");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");

        Out.printf("==NEIGHBORS [c]==\n");
        nbrs = graph.getNeibs("c");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");

        Out.printf("==NEIGHBORS [d]==\n");
        nbrs = graph.getNeibs("d");
        for (int i = 0; i < nbrs.size(); ++i) {
            Out.printf("[%s] ", nbrs.get(i));
        }
        Out.print("\n");
    }

    @Test
    void tsortadjlTest() {
        Out.println("==[TOPO SORT] [ADJACENCY LIST]==");
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