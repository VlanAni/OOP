package vanisimov.expression.application;

import java.io.IOException;
import vanisimov.expression.customio.FileIO;
import vanisimov.expression.customio.StdIO;
import vanisimov.expression.exceptions.ArgsErrors;
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
                StdIO.printf(Consts.inputFilepath);
                App.filePath = StdIO.readStr();
                while (App.filePath == null) {
                    StdIO.printf(ErrorsMessages.noInput);
                    StdIO.printf(Consts.inputFilepath);
                    App.filePath = StdIO.readStr();
                }
                StdIO.printf(Consts.inputEncoding);
                App.fileEncoding = StdIO.readStr();
                while (App.fileEncoding == null) {
                    StdIO.printf(ErrorsMessages.noInput);
                    StdIO.printf(Consts.inputEncoding);
                    App.fileEncoding = StdIO.readStr();
                }
                try {
                    FileIO.openFile(App.filePath, App.fileEncoding);
                    successOpened = true;
                } catch (IOException e) {
                    StdIO.print(ErrorsMessages.noFile);
                }
            }
            App.processedExp = FileIO.readFileline();
            if (App.processedExp == null) {
                StdIO.print(ErrorsMessages.emptyFile);
                App.closeDialog();
                FileIO.closeFile();
                return;
            }
            FileIO.closeFile();
        } else {
            StdIO.print(Consts.inputExp);
            App.processedExp = StdIO.readStr();
            while (App.processedExp == null) {
                StdIO.print(ErrorsMessages.emptyFile);
                App.processedExp = StdIO.readStr();
            }
        }

        // printing information about expression
        App.exploreExp();

        App.closeDialog();
    }

    static void openDialog() {
        StdIO.openStdin();
    }

    static void start() {
        StdIO.print(Consts.greeting);
        StdIO.print(Consts.userInput);
    }

    static void readChoice() {
        String choice = StdIO.readStr();
        if (choice == null) {
            StdIO.print(ErrorsMessages.noInput);
        } else {
            try {
                App.fileSrc = Integer.parseInt(choice) == 1;
            } catch (NumberFormatException e) {
                App.fileSrc = false;
            }
        }
    }

    static void exploreExp() {

        StdIO.print(Consts.inputVar);
        String var = StdIO.readStr();
        while (var == null) {
            StdIO.print(ErrorsMessages.noInput);
            var = StdIO.readStr();
        }

        Expression exp = Expression.makeExp(App.processedExp);
        StdIO.printf(Consts.showDer, var);
        exp.derivative(var).printExp();
        StdIO.newStr();

        StdIO.print(Consts.inputVales);
        boolean evalSuccess = false;
        String varValues = null;
        while (!evalSuccess) {

            varValues = StdIO.readStr();
            while (varValues == null) {
                StdIO.print(ErrorsMessages.noInput);
                varValues = StdIO.readStr();
            }

            int value;
            try {
                value = exp.eval(varValues);
                StdIO.printf(Consts.showVal, value);
                evalSuccess = true;
            } catch (ArgsErrors e) {
                StdIO.print(ErrorsMessages.wrongFormat);
            }
        }
    }

    static void closeDialog() {
        StdIO.closeStdin();
    }

}