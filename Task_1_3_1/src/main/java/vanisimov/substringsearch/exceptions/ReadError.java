package vanisimov.substringsearch.exceptions;

import java.io.IOException;

public class ReadError extends IOException {
    public ReadError(String message) {
        super(message);
    }
}
