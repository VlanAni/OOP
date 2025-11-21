import java.io.*;
import java.nio.charset.StandardCharsets;

class FileGenerator {

    static File create(String content) throws IOException {
        File file = File.createTempFile("test_input", ".txt");
        file.deleteOnExit();
        try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
            writer.print(content);
        }
        return file;
    }

}
