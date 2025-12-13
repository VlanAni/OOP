package vanisimov.substringsearch.components;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class Source implements AutoCloseable {

    private final BufferedReader in;

    Source(String filename) throws IOException {
        try {
            this.in = new BufferedReader(new FileReader(filename,
                    StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IOException("--Cannot open the file--");
        }
    }

    @Override
    public void close() throws IOException {
        try {
            this.in.close();
        } catch (IOException e) {
            throw new IOException("File has been already closed");
        }
    }

    int getCodePoint() throws IOException {
        int r = in.read();
        if (r == -1) {
            throw new EOFException("EOF");
        }
        char c = (char) r;

        if (!Character.isHighSurrogate(c)) {
            return c;
        }

        in.mark(1);
        int r2 = in.read();
        if (r2 == -1) {
            return c;
        }
        char low = (char) r2;
        if (Character.isLowSurrogate(low)) {
            return Character.toCodePoint(c, low);
        } else {
            in.reset();
            return c;
        }
    }
}