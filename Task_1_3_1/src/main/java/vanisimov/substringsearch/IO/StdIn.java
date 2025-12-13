package vanisimov.substringsearch.IO;

import java.util.Scanner;

public class StdIn implements AutoCloseable {

    private final Scanner input;

    public StdIn () {
        this.input = new Scanner(System.in);
    }

    public String readString() {
        if (this.input.hasNext()) {
            return this.input.nextLine();
        } else {
            return null;
        }
    }

    public void close() {
        if (this.input != null) {
            this.input.close();
        }
    }

}
