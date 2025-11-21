import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import vanisimov.substringsearch.components.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestEmptyCase {

    @Test
    public void testEmptyFile() throws IOException {
        File file = FileGenerator.create("");
        List<Integer> result = Controller.find(file.getPath(), "abc");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testEmptyPattern() throws IOException {
        File file = FileGenerator.create("some text content");
        List<Integer> result = Controller.find(file.getPath(), "");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testEmptyFileAndPattern() throws IOException {
        File file = FileGenerator.create("");
        List<Integer> result = Controller.find(file.getPath(), "");
        Assertions.assertTrue(result.isEmpty());
    }
}