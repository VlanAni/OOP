package vanisimov.expression.exceptions;

/**
 * Possible messages for exceptions.
 */
public class ErrorsMessages {
    public static final String noValues = "ERROR: you haven't any values for variables!";
    public static final String noVar = "ERROR: you haven't value for the variable!";
    public static final String formatErr = "ERROR: value MUST be in this format: [name] = [value]";
    public static final String noInput = "NO INPUT FROM USER\n";
    public static final String noFile = "NO SUCH FILE\n";
    public static final String emptyFile = "EMPTY FILE\n";
    public static final String wrongFormat = "WRONG VALUES FORMAT:" +
            " values must be in such a format: [v1 = val; v2 = val;...]." +
            " The list must include values for all variables\n";
}