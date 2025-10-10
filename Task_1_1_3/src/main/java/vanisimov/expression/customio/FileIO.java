package vanisimov.expression.customio;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileIO {

    private static Scanner fileIn;

    public static void openFile(String path, String encoding) throws IOException {
        FileIO.fileIn = new Scanner(new File(path), encoding);
    }

    public static String readFileline() {
        if (FileIO.fileIn.hasNext()) {
            return FileIO.fileIn.nextLine();
        } else {
            return null;
        }
    }

    public static void closeFile() {
        FileIO.fileIn.close();
    }
}