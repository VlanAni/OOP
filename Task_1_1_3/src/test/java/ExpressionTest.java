import org.junit.jupiter.api.Test;
import vanisimov.expression.customio.StdIO;
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
        StdIO.print("==Testing Number==\n");
        Number num = new Number(125);
        Expression numDer = num.derivative("y");
        num.printExp();
        StdIO.newStr();
        numDer.printExp();
        StdIO.newStr();
        int val = num.eval("y = 12");
        StdIO.printf("Value: [%d]", val);
        StdIO.newStr();
    }

    @Test
    void varTest() {
        StdIO.print("==Testing variable==\n");
        Variable var = new Variable("vovchik");
        var.printExp();
        StdIO.newStr();
        Expression varDer = var.derivative("y");
        varDer.printExp();
        StdIO.newStr();
        Expression varDer1 = var.derivative("vovchik");
        varDer1.printExp();
        StdIO.newStr();
    }

    @Test
    void addTest() {
        StdIO.print("==Testing Add class==\n");
        Add addition = new Add(new Number(5), new Variable("x"));
        addition.printExp();
        StdIO.newStr();
        Expression der = addition.derivative("x");
        der.printExp();
        StdIO.newStr();
        try {
            int value = addition.eval("x = 10");
            StdIO.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdIO.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void subTest() {
        StdIO.print("==Testing Sub class==\n");
        Sub sub = new Sub(new Number(123), new Number(-23));
        sub.printExp();
        StdIO.newStr();
        Expression der = sub.derivative("x");
        der.printExp();
        StdIO.newStr();
        try {
            int value = sub.eval("x = 10");
            StdIO.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdIO.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void mulTest() {
        StdIO.print("==Testing Mul class==\n");
        Mul mul = new Mul(new Number(51), new Add(new Number(123), new Variable("x")));
        mul.printExp();
        StdIO.newStr();
        Expression der = mul.derivative("x");
        der.printExp();
        StdIO.newStr();
        try {
            int value = mul.eval("x = 10");
            StdIO.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdIO.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void divTest() {
        StdIO.print("==Testing Div class==\n");
        Div div = new Div(new Number(51), new Add(new Number(123), new Variable("x")));
        div.printExp();
        StdIO.newStr();
        Expression der = div.derivative("x");
        der.printExp();
        StdIO.newStr();
        try {
            int value = div.eval("x = 10");
            StdIO.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdIO.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void parseOne() {
        StdIO.print("==Testing parsing №1==\n");
        Expression exp = Expression.makeExp("(((y * x) / (5 + 8)) - (y + z))");
        exp.printExp();
        StdIO.newStr();
        try {
            int value = exp.eval("x = 10;y = 13;z=5");
            StdIO.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdIO.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void parseTwo() {
        StdIO.print("==Testing parsing №2==\n");
        Expression exp = Expression.makeExp("((5*x) + (6*y))");
        exp.printExp();
        StdIO.newStr();
        try {
            int value = exp.eval("x = 10; y = 13");
            StdIO.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            StdIO.printf(ErrorsMessages.wrongFormat);
        }
        StdIO.newStr();
        Expression derX = exp.derivative("x");
        StdIO.print("DerX: ");
        derX.printExp();
        StdIO.newStr();
        Expression derY = exp.derivative("y");
        StdIO.print("DerY: ");
        derY.printExp();
        StdIO.newStr();
    }
}