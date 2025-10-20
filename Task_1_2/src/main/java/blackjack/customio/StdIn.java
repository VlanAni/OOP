package blackjack.customio;

import java.util.Scanner;

public class StdIn implements AutoCloseable {

    private Scanner usrIn;

    public StdIn() {
        this.usrIn = new Scanner(System.in);
    }

    public int scanInt() {
        int scannedValue = this.usrIn.nextInt();
        return scannedValue;
    }

    @Override
    public void close() {
        this.usrIn.close();
    }

}