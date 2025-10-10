package vanisimov.expression.customio;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Fileio {

    private static Scanner fileIn;

    public static void openFile(String path, String encoding) throws IOException {
        Fileio.fileIn = new Scanner(new File(path), encoding);
    }

    public static String readFileline() {
        if (Fileio.fileIn.hasNext()) {
            return Fileio.fileIn.nextLine();
        } else {
            return null;
        }
    }

    public static void closeFile() {
        Fileio.fileIn.close();
    }
}