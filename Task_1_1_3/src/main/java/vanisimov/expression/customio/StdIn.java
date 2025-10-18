package vanisimov.expression.customio;

import java.util.Scanner;

public class StdIn {

    private static Scanner stdIn;

    /**
     * Open scanner with the standard input.
     */
    public static void openStdin() {
        StdIn.stdIn = new Scanner(System.in);
    }

    /**
     * Read a string.
     *
     * @return - string that has been string.
     */
    public static String readStr() {
        if (StdIn.stdIn.hasNext()) {
            return StdIn.stdIn.nextLine();
        } else {
            return null;
        }
    }

    /**
     * Closing a scanner.
     */
    public static void closeStdin() {
        StdIn.stdIn.close();
    }

}