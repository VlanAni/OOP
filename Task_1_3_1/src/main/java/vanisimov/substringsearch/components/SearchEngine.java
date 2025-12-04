package vanisimov.substringsearch.components;

import vanisimov.substringsearch.exceptions.ReadError;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine {

    private final String subString;
    private int[] prefFunc;
    private final Source fh;

    SearchEngine(Source resource, String subString) {
        this.fh = resource;
        this.subString = subString;
        this.calcPrefFunc();
    }

    List<Integer> findAll() throws ReadError {
        List<Integer> occurrences = new ArrayList<>();
        if (subString.isEmpty()) {
            return occurrences;
        }
        int j = 0;
        int position = 0;
        try {
            while (true) {
                char c = fh.getChar();
                while (j > 0 && c != subString.charAt(j)) {
                    j = prefFunc[j - 1];
                }
                if (c == subString.charAt(j)) {
                    j++;
                }
                if (j == subString.length()) {
                    int startPosition = position - subString.length() + 1;
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
        this.prefFunc = new int[this.subString.length()];
        for (int i = 1; i < this.prefFunc.length; ++i) {
            int cur = this.prefFunc[i - 1];
            while (this.subString.charAt(i) != this.subString.charAt(cur) &&
                    cur > 0) {
                cur = this.prefFunc[cur - 1];
            }
            if (this.subString.charAt(i) == this.subString.charAt(cur)) {
                this.prefFunc[i] = cur + 1;
            }
        }
    }
}