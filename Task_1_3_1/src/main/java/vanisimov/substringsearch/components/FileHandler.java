package vanisimov.substringsearch.components;

import vanisimov.substringsearch.IO.StdOut;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class FileHandler implements AutoCloseable {

    private static int standardCap = 2048;
    private int arrayCapacity; // ёмкость массива
    private int arraySize; // сколько символов хранит массив
    private String filename; // имя файла
    private char[] charArray;
    private int arrayPoint; // the position in the array
    private int totalChardRead; // Amount of characters those are read from the file
    private BufferedReader in;

    FileHandler(String filename) throws IOException {
        try {
            this.in = new BufferedReader(new FileReader(filename,
                    StandardCharsets.UTF_8));
            this.filename = filename;
            this.arrayCapacity = FileHandler.standardCap;
            this.arrayPoint = 0;
            this.totalChardRead = 0;
            charArray = new char[this.arrayCapacity];
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
        if (this.arrayPoint >= this.arraySize) {
            try {
                if (!this.fillBuffer()) {
                    throw new EOFException("End of file reached");
                }
            } catch (EOFException e) {
                throw new EOFException("End of file reached");
            } catch (IOException e) {
                throw new IOException("I/O error [getChar]");
            }
        }
        char currChar = this.charArray[this.arrayPoint];
        this.arrayPoint++;
        this.totalChardRead++;
        return currChar;
    }

    private boolean fillBuffer() throws IOException {
        try {
            int n = this.in.read(this.charArray, 0, this.arrayCapacity);
            this.arrayPoint = 0;
            if (n != -1) {
                this.arraySize = n;
                return true;
            } else {
                this.arraySize = 0;
                return false;
            }
        } catch (IOException e) {
            throw new IOException("I/O error [read]");
        }
    }
}