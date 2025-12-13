package vanisimov.substringsearch.components;

import vanisimov.substringsearch.exceptions.ReadError;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine {

    private final int[] pattern;
    private int[] prefFunc;
    private final Source fh;

    SearchEngine(Source resource, String subString) {
        this.fh = resource;
        this.pattern = subString.codePoints().toArray();
        this.calcPrefFunc();
    }

    List<Long> findAll() throws ReadError {
        List<Long> occurrences = new ArrayList<>();
        if (pattern.length == 0) {
            return occurrences;
        }
        int j = 0;
        long position = 0;
        try {
            while (true) {
                int c = fh.getCodePoint();
                while (j > 0 && c != this.pattern[j]) {
                    j = prefFunc[j - 1];
                }
                if (c == this.pattern[j]) {
                    j++;
                }
                if (j == this.pattern.length) {
                    long startPosition = position - pattern.length + 1;
                    occurrences.add(startPosition);
                    j = prefFunc[j - 1];
                }
                position++;
            }
        } catch (EOFException e) { // EOF
            return occurrences;
        } catch (IOException e) {
            throw new ReadError("IO-error"); // IO-error
        }
    }

    private void calcPrefFunc() {
        this.prefFunc = new int[this.pattern.length];
        for (int i = 1; i < this.prefFunc.length; ++i) {
            int cur = this.prefFunc[i - 1];
            while (this.pattern[i] != this.pattern[cur] &&
                    cur > 0) {
                cur = this.prefFunc[cur - 1];
            }
            if (this.pattern[i] == this.pattern[cur]) {
                this.prefFunc[i] = cur + 1;
            }
        }
    }
}