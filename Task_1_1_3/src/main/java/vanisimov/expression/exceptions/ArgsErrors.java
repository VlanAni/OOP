package vanisimov.expression.exceptions;

/**
 * Implementing runtime exceptions for args.
 */
public class ArgsErrors extends RuntimeException {

    /**
     * Creating an exception.
     *
     * @param message - message to show.
     */
    public ArgsErrors(String message) {
        super(message);
    }

}