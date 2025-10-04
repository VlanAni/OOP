package blackjack.customio;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Implementnig custom io-system.
 */
public class InOut {

    private Scanner usrIn;

    public InOut() {}

    public static void println(String string) {
        System.out.println(string);
    }

    public static void printfInt(String string, int... args) {
        System.out.printf(string, Arrays.stream(args).boxed().toArray());
    }

    public static void printfStr(String string, String... args) {
        System.out.printf(string, (Object[]) args);
    }

    public static void print(String string) {
        System.out.print(string);
    }

    public void openStdIn() {
        this.usrIn = new Scanner(System.in);
    }

    public int scanInt() {
        int scannedValue = this.usrIn.nextInt();
        return scannedValue;
    }

    public void closeStdIn() {
        this.usrIn.close();
    }

}