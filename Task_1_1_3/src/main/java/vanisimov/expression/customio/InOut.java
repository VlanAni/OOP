package vanisimov.expression.customio;

import java.util.Scanner;

/**
 * Implementing custom io-system.
 */
public class InOut {

    private static Scanner stdIn;

    private InOut() {
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
    public static void openInput() {
        InOut.stdIn = new Scanner(System.in);
    }

    /**
     * Read int value.
     *
     * @return - value that has been read.
     */
    public static int readInt() {
        return InOut.stdIn.nextInt();
    }

    /**
     * Read a string.
     *
     * @return - string that has been string.
     */
    public static String readStr() {
        return InOut.stdIn.nextLine();
    }

    /**
     * Closing a scanner.
     */
    public static void closeInput() {
        InOut.stdIn.close();
    }
}