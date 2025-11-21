import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vanisimov.substringsearch.components.Controller;

public class TestNormalCase {
    @Test
    public void testTaskExample() throws IOException {
        File file = FileGenerator.create("абракадабра");
        List<Integer> result = Controller.find(file.getPath(), "бра");
        Assertions.assertEquals(List.of(1, 8), result);
    }

    @Test
    public void testStartAndEnd() throws IOException {
        String text = "Hello world! Hello";
        File file = FileGenerator.create(text);
        List<Integer> result = Controller.find(file.getPath(), "Hello");
        Assertions.assertEquals(List.of(0, 13), result);
    }

    @Test
    public void testMultipleOccurrences() throws IOException {
        String text = "one one one";
        File file = FileGenerator.create(text);
        List<Integer> result = Controller.find(file.getPath(), "one");
        Assertions.assertEquals(List.of(0, 4, 8), result);
    }

    @Test
    public void testOverlappingStrings() throws IOException {
        File file = FileGenerator.create("aaaaa");
        List<Integer> result = Controller.find(file.getPath(), "aa");
        Assertions.assertEquals(List.of(0, 1, 2, 3), result);
    }

    @Test
    public void testPatternLongerThanFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        Collection<Integer> indexes = new ArrayList<Integer>();
        String subStr = "loooooong pattern";
        for (int i = 1; i <= 1500; ++i) {
            sb.append(subStr).append('\n');
            indexes.add((i - 1) * (subStr.length() + 1));
        }
        File file = FileGenerator.create(sb.toString());
        List<Integer> result = Controller.find(file.getPath(), subStr);
        Assertions.assertTrue(result.containsAll(indexes));
    }
}