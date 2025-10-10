package vanisimov.expression.exceptions;

import java.io.IOException;

/**
 * Implementing runtime exceptions for args.
 */
public class ArgsErrors extends IOException {

    /**
     * Creating an exception.
     *
     * @param message - message to show.
     */
    public ArgsErrors(String message) {
        super(message);
    }
}