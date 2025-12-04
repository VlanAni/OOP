package vanisimov.substringsearch.components;

import java.io.IOException;
import java.util.List;
import vanisimov.substringsearch.IO.StdIn;
import vanisimov.substringsearch.IO.StdOut;
import vanisimov.substringsearch.exceptions.ReadError;

public class Controller {

    private static String fileName;
    private static String subString;

    public static void runApp() {
        if (!Controller.readUserInput()) {
            return;
        }
        List<Integer> result = Controller.find(Controller.fileName, Controller.subString);
        if (result == null) {
            StdOut.println("Errors occurs: IO-error");
        } else {
            StdOut.println(result);
        }
    }

    public static List<Integer> find(String filePath, String subString) {
        try (Source fh = new Source(filePath)) {
            SearchEngine schEng = new SearchEngine(fh, subString);
            return schEng.findAll();
        } catch (ReadError e) {
            StdOut.println("--Read error occurred--");
            return null;
        } catch (IOException e) {
            StdOut.println("--Cannot open your file--");
            return null;
        }
    }

    private static boolean readUserInput() {
        try (StdIn in = new StdIn()) {
            StdOut.print("Enter filename: ");
            Controller.fileName = in.readString();
            if (Controller.fileName == null) {
                StdOut.println("No any user input...");
                return false;
            }
            StdOut.print("Enter substring: ");
            Controller.subString = in.readString();
            if (Controller.subString == null) {
                StdOut.println("No any user input...");
                return false;
            }
            return true;
        }
    }
}