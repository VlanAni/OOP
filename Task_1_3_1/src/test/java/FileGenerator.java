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

    static File createLargeChars(long charCount, String pattern) throws IOException {
        File file = File.createTempFile("test_large_input", ".txt");
        file.deleteOnExit();

        try (BufferedWriter out = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            long written = 0;
            int patLen = pattern.length();

            while (written + patLen <= charCount) {
                out.write(pattern);
                written += patLen;
            }

            int tail = (int) (charCount - written);
            if (tail > 0) {
                out.write(pattern, 0, tail);
            }
        }

        return file;
    }

}
