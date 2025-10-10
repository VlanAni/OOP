package vanisimov.expression.customio;

import java.util.Scanner;

/**
 * Implementing custom io-system.
 */
public class Stdio {

    private static Scanner stdIn;

    private Stdio() {
    }

    /**
     * printf.
     *
     * @param st - string to print.
     * @param args - agrs to print.
     */
    public static void printf(String st, Object... args) {
        System.out.printf(st, args);
    }

    /**
     * print.
     *
     * @param st - string to print.
     */
    public static void print(String st) {
        System.out.print(st);
    }

    /**
     * Replace to the next string.
     */
    public static void newStr() {
        System.out.print("\n");
    }

    /**
     * Open scanner with the standard input.
     */
    public static void openStdin() {
        Stdio.stdIn = new Scanner(System.in);
    }

    /**
     * Read a string.
     *
     * @return - string that has been string.
     */
    public static String readStr() {
        if (Stdio.stdIn.hasNext()) {
            return Stdio.stdIn.nextLine();
        } else {
            return null;
        }
    }

    /**
     * Closing a scanner.
     */
    public static void closeStdin() {
        Stdio.stdIn.close();
    }
}