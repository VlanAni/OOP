import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import vanisimov.substringsearch.components.Controller;

public class TestEmptyCase {

    @Test
    public void testEmptyFile() throws IOException {
        try {
            File file = FileGenerator.create("");
            List<Long> result = Controller.find(file.getPath(), "abc");

            try {
                Assertions.assertTrue(result.isEmpty());
            } catch (NullPointerException e) {

                assert (false);

            }

        } catch (Exception e) {

            assert (false);

        }
    }

    @Test
    public void testEmptyPattern() throws IOException {
        try {
            File file = FileGenerator.create("some text content");
            List<Long> result = Controller.find(file.getPath(), "");

            try {
                Assertions.assertTrue(result.isEmpty());
            } catch (NullPointerException e) {

                assert (false);

            }

        } catch (Exception e) {

            assert (false);

        }
    }

    @Test
    public void testEmptyFileAndPattern() throws IOException {
        try {
            File file = FileGenerator.create("");
            List<Long> result = Controller.find(file.getPath(), "");

            try {
                Assertions.assertTrue(result.isEmpty());
            } catch (NullPointerException e) {

                assert (false);

            }

        } catch (Exception e) {

            assert (false);

        }
    }
}