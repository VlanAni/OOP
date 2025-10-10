package vanisimov.expression.application;

import java.io.IOException;
import vanisimov.expression.customio.Fileio;
import vanisimov.expression.customio.Stdio;
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
                Stdio.printf(Consts.inputFilepath);
                App.filePath = Stdio.readStr();
                while (App.filePath == null) {
                    Stdio.printf(ErrorsMessages.noInput);
                    Stdio.printf(Consts.inputFilepath);
                    App.filePath = Stdio.readStr();
                }
                Stdio.printf(Consts.inputEncoding);
                App.fileEncoding = Stdio.readStr();
                while (App.fileEncoding == null) {
                    Stdio.printf(ErrorsMessages.noInput);
                    Stdio.printf(Consts.inputEncoding);
                    App.fileEncoding = Stdio.readStr();
                }
                try {
                    Fileio.openFile(App.filePath, App.fileEncoding);
                    successOpened = true;
                } catch (IOException e) {
                    Stdio.print(ErrorsMessages.noFile);
                }
            }
            App.processedExp = Fileio.readFileline();
            if (App.processedExp == null) {
                Stdio.print(ErrorsMessages.emptyFile);
                App.closeDialog();
                Fileio.closeFile();
                return;
            }
            Fileio.closeFile();
        } else {
            Stdio.print(Consts.inputExp);
            App.processedExp = Stdio.readStr();
            while (App.processedExp == null) {
                Stdio.print(ErrorsMessages.emptyFile);
                App.processedExp = Stdio.readStr();
            }
        }

        // printing information about expression
        App.exploreExp();

        App.closeDialog();
    }

    static void openDialog() {
        Stdio.openStdin();
    }

    static void start() {
        Stdio.print(Consts.greeting);
        Stdio.print(Consts.userInput);
    }

    static void readChoice() {
        String choice = Stdio.readStr();
        if (choice == null) {
            Stdio.print(ErrorsMessages.noInput);
        } else {
            try {
                App.fileSrc = Integer.parseInt(choice) == 1;
            } catch (NumberFormatException e) {
                App.fileSrc = false;
            }
        }
    }

    static void exploreExp() {

        Stdio.print(Consts.inputVar);
        String var = Stdio.readStr();
        while (var == null) {
            Stdio.print(ErrorsMessages.noInput);
            var = Stdio.readStr();
        }

        Expression exp = Expression.makeExp(App.processedExp);
        Stdio.printf(Consts.showDer, var);
        exp.derivative(var).printExp();
        Stdio.newStr();

        Stdio.print(Consts.inputVales);
        boolean evalSuccess = false;
        String varValues = null;
        while (!evalSuccess) {

            varValues = Stdio.readStr();
            while (varValues == null) {
                Stdio.print(ErrorsMessages.noInput);
                varValues = Stdio.readStr();
            }

            int value;
            try {
                value = exp.eval(varValues);
                Stdio.printf(Consts.showVal, value);
                evalSuccess = true;
            } catch (ArgsErrors e) {
                Stdio.print(ErrorsMessages.wrongFormat);
            }
        }
    }

    static void closeDialog() {
        Stdio.closeStdin();
    }

}