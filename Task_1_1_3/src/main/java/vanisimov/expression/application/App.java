package vanisimov.expression.application;

import java.io.IOException;
import vanisimov.expression.customio.FileIO;
import vanisimov.expression.customio.StdIn;
import vanisimov.expression.customio.StdOut;
import vanisimov.expression.exceptions.DivZero;
import vanisimov.expression.exceptions.ValErrors;
import vanisimov.expression.exceptions.ErrorsMessages;
import vanisimov.expression.expressionskinds.Expression;

class App {

    private static Boolean fileSrc = null;
    private static String filePath = null;
    private static String fileEncoding = null;
    private static String processedExp = null;

    private App() {}

    static void run() {

        // starting the app
        App.start();
        App.openDialog();

        // reading choice
        App.readChoice();
        while (App.fileSrc == null) {
            App.readChoice();
        }

        // reading expression
        if (App.fileSrc) {
            boolean successOpened = false;
            while (!successOpened) {
                StdOut.printf(Consts.inputFilepath);
                App.filePath = StdIn.readStr();
                while (App.filePath == null) {
                    StdOut.printf(ErrorsMessages.noInput);
                    StdOut.printf(Consts.inputFilepath);
                    App.filePath = StdIn.readStr();
                }
                StdOut.printf(Consts.inputEncoding);
                App.fileEncoding = StdIn.readStr();
                while (App.fileEncoding == null) {
                    StdOut.printf(ErrorsMessages.noInput);
                    StdOut.printf(Consts.inputEncoding);
                    App.fileEncoding = StdIn.readStr();
                }
                try {
                    FileIO.openFile(App.filePath, App.fileEncoding);
                    successOpened = true;
                } catch (IOException e) {
                    StdOut.print(ErrorsMessages.noFile);
                }
            }
            App.processedExp = FileIO.readFileline();
            if (App.processedExp == null) {
                StdOut.print(ErrorsMessages.emptyFile);
                App.closeDialog();
                FileIO.closeFile();
                return;
            }
            FileIO.closeFile();
        } else {
            StdOut.print(Consts.inputExp);
            App.processedExp = StdIn.readStr();
            while (App.processedExp == null) {
                StdOut.print(ErrorsMessages.emptyFile);
                App.processedExp = StdIn.readStr();
            }
        }

        // printing information about expression
        App.exploreExp();
        App.closeDialog();

    }

    static void openDialog() {
        StdIn.openStdin();
    }

    static void start() {
        StdOut.print(Consts.greeting);
        StdOut.print(Consts.userInput);
    }

    static void readChoice() {
        String choice = StdIn.readStr();
        if (choice == null) {
            StdOut.print(ErrorsMessages.noInput);
        } else {
            try {
                App.fileSrc = Integer.parseInt(choice) == 1;
            } catch (NumberFormatException e) {
                App.fileSrc = false;
            }
        }
    }

    static void exploreExp() {

        StdOut.print(Consts.inputVar);
        String var = StdIn.readStr();
        while (var == null) {
            StdOut.print(ErrorsMessages.noInput);
            var = StdIn.readStr();
        }

        Expression exp = Expression.makeExp(App.processedExp);
        StdOut.printf(Consts.showDer, var.trim());
        exp.derivative(var).print();
        StdOut.newStr();

        StdOut.print(Consts.inputVales);
        boolean evalSuccess = false;
        String varValues = null;
        while (!evalSuccess) {

            varValues = StdIn.readStr();
            while (varValues == null) {
                StdOut.print(ErrorsMessages.noInput);
                varValues = StdIn.readStr();
            }

            int value;
            try {
                value = exp.eval(varValues);
                StdOut.printf(Consts.showVal, value);
                evalSuccess = true;
            } catch (ValErrors e) {
                StdOut.print(ErrorsMessages.wrongFormat);
            } catch (DivZero e) {
                StdOut.print(ErrorsMessages.zerdiv);
            }
        }
    }

    static void closeDialog() {
        StdIn.closeStdin();
    }

}