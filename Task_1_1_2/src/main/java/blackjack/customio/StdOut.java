package blackjack.customio;

public class StdOut {

    private StdOut() {
    }

    public static void println(String string) {
        System.out.println(string);
    }

    public static void printf(String string, Object... args) {
        System.out.printf(string, args);
    }

    public static void print(String string) {
        System.out.print(string);
    }

}