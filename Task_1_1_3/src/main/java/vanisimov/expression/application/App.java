package vanisimov.expression.application;

import vanisimov.expression.customio.InOut;
import vanisimov.expression.expressionskinds.Expression;

class App {

    private App() {}

    static void start() {
        InOut.print(Consts.greeting);
        InOut.print(Consts.userInput);
        InOut.openInput();
        String input = InOut.readStr();
        Expression exp = Expression.makeExp(input);
        InOut.print(Consts.inputVar);
        String var = InOut.readStr();
        Expression der = exp.derivative(var);
        InOut.printf(Consts.showDer);
        der.printExp();
        InOut.newStr();
        InOut.print(Consts.inputVales);
        String values = InOut.readStr();
        InOut.newStr();
        int val = exp.eval(values);
        InOut.printf(Consts.showVal, val);
        InOut.closeInput();
    }

}