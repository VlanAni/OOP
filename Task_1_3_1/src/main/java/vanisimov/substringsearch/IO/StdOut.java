package vanisimov.substringsearch.IO;

public class StdOut {

    private StdOut () {
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void printf(String string, Object... args) {
        System.out.printf(string, args);
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }

}
