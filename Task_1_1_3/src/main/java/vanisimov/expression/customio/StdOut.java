package vanisimov.expression.customio;

/**
 * Implementing custom io-system.
 */
public class StdOut {

    private StdOut() {
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

}