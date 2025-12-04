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

    char getChar() throws IOException {
        try {
            int c = this.in.read();
            if (c == -1) {
                throw new EOFException("End of file reached");
            }
            return (char) c;
        } catch (EOFException e) {
            throw new EOFException("End of file reached");
        } catch (IOException e) {
            throw new IOException("I/O error");
        }
    }
}