package vanisimov.graphdevkit.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class In implements AutoCloseable {

    private Scanner input;

    public In (String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner inputSc = new Scanner(file);
        this.input = inputSc;
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