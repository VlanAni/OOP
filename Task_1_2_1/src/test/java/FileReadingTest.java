import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import vanisimov.graphdevkit.graphint.Graph;
import vanisimov.graphdevkit.io.Out;

class FileReadingTest {

    @Test
    void fileReading1() {
        String filepath = "test1.txt";
        URL resource = getClass().getClassLoader().getResource(filepath);
        try {
            filepath = Paths.get(resource.toURI()).toAbsolutePath().toString();
            Graph graph = Graph.readFile(filepath);
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
            Graph graph = Graph.readFile(filepath);

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
            Graph graph = Graph.readFile(filepath);
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
            Graph graph = Graph.readFile(filepath);

            assert (graph != null);

        } catch (URISyntaxException e) {
            Out.println("Error with URI");
        }
    }
}