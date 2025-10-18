import org.junit.jupiter.api.Test;
import vanisimov.expression.customio.StdOut;
import vanisimov.expression.exceptions.ArgsErrors;
import vanisimov.expression.exceptions.ErrorsMessages;
import vanisimov.expression.expressionskinds.Add;
import vanisimov.expression.expressionskinds.Div;
import vanisimov.expression.expressionskinds.Expression;
import vanisimov.expression.expressionskinds.Mul;
import vanisimov.expression.expressionskinds.Number;
import vanisimov.expression.expressionskinds.Sub;
import vanisimov.expression.expressionskinds.Variable;

class ExpressionTest {

    @Test
    void numberTest() {
        StdOut.print("==Testing Number==\n");
        Number num = new Number(125);
        Expression numDer = num.derivative("y");
        num.printExp();
        StdOut.newStr();
        numDer.printExp();
        StdOut.newStr();
        int val = num.eval("y = 12");
        StdOut.printf("Value: [%d]", val);
        StdOut.newStr();
    }

    @Test
    void varTest() {
        StdOut.print("==Testing variable==\n");
        Variable var = new Variable("vovchik");
        var.printExp();
        StdOut.newStr();
        Expression varDer = var.derivative("y");
        varDer.printExp();
        StdOut.newStr();
        Expression varDer1 = var.derivative("vovchik");
        varDer1.printExp();
        StdOut.newStr();
    }

    @Test
    void addTest() {
        StdOut.print("==Testing Add class==\n");
        Add addition = new Add(new Number(5), new Variable("x"));
        addition.printExp();
        StdOut.newStr();
        Expression der = addition.derivative("x");
        der.printExp();
        StdOut.newStr();
        try {
            int value = addition.eval("x = 10");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void subTest() {
        StdOut.print("==Testing Sub class==\n");
        Sub sub = new Sub(new Number(123), new Number(-23));
        sub.printExp();
        StdOut.newStr();
        Expression der = sub.derivative("x");
        der.printExp();
        StdOut.newStr();
        try {
            int value = sub.eval("x = 10");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void mulTest() {
        StdOut.print("==Testing Mul class==\n");
        Mul mul = new Mul(new Number(51), new Add(new Number(123), new Variable("x")));
        mul.printExp();
        StdOut.newStr();
        Expression der = mul.derivative("x");
        der.printExp();
        StdOut.newStr();
        try {
            int value = mul.eval("x = 10");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void divTest() {
        StdOut.print("==Testing Div class==\n");
        Div div = new Div(new Number(51), new Add(new Number(123), new Variable("x")));
        div.printExp();
        StdOut.newStr();
        Expression der = div.derivative("x");
        der.printExp();
        StdOut.newStr();
        try {
            int value = div.eval("x = 10");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void parseOne() {
        StdOut.print("==Testing parsing №1==\n");
        Expression exp = Expression.makeExp("(((y * x) / (5 + 8)) - (y + z))");
        exp.printExp();
        StdOut.newStr();
        try {
            int value = exp.eval("x = 10;y = 13;z=5");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void parseTwo() {
        StdOut.print("==Testing parsing №2==\n");
        Expression exp = Expression.makeExp("((5*x) + (6*y))");
        exp.printExp();
        StdOut.newStr();
        try {
            int value = exp.eval("x = 10; y = 13");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        }
        StdOut.newStr();
        Expression derX = exp.derivative("x");
        StdOut.print("DerX: ");
        derX.printExp();
        StdOut.newStr();
        Expression derY = exp.derivative("y");
        StdOut.print("DerY: ");
        derY.printExp();
        StdOut.newStr();
    }
}