import org.junit.jupiter.api.Test;
import vanisimov.expression.customio.Stdio;
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
        Stdio.print("==Testing Number==\n");
        Number num = new Number(125);
        Expression numDer = num.derivative("y");
        num.printExp();
        Stdio.newStr();
        numDer.printExp();
        Stdio.newStr();
        int val = num.eval("y = 12");
        Stdio.printf("Value: [%d]", val);
        Stdio.newStr();
    }

    @Test
    void varTest() {
        Stdio.print("==Testing variable==\n");
        Variable var = new Variable("vovchik");
        var.printExp();
        Stdio.newStr();
        Expression varDer = var.derivative("y");
        varDer.printExp();
        Stdio.newStr();
        Expression varDer1 = var.derivative("vovchik");
        varDer1.printExp();
        Stdio.newStr();
    }

    @Test
    void addTest() {
        Stdio.print("==Testing Add class==\n");
        Add addition = new Add(new Number(5), new Variable("x"));
        addition.printExp();
        Stdio.newStr();
        Expression der = addition.derivative("x");
        der.printExp();
        Stdio.newStr();
        try {
            int value = addition.eval("x = 10");
            Stdio.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            Stdio.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void subTest() {
        Stdio.print("==Testing Sub class==\n");
        Sub sub = new Sub(new Number(123), new Number(-23));
        sub.printExp();
        Stdio.newStr();
        Expression der = sub.derivative("x");
        der.printExp();
        Stdio.newStr();
        try {
            int value = sub.eval("x = 10");
            Stdio.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            Stdio.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void mulTest() {
        Stdio.print("==Testing Mul class==\n");
        Mul mul = new Mul(new Number(51), new Add(new Number(123), new Variable("x")));
        mul.printExp();
        Stdio.newStr();
        Expression der = mul.derivative("x");
        der.printExp();
        Stdio.newStr();
        try {
            int value = mul.eval("x = 10");
            Stdio.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            Stdio.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void divTest() {
        Stdio.print("==Testing Div class==\n");
        Div div = new Div(new Number(51), new Add(new Number(123), new Variable("x")));
        div.printExp();
        Stdio.newStr();
        Expression der = div.derivative("x");
        der.printExp();
        Stdio.newStr();
        try {
            int value = div.eval("x = 10");
            Stdio.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            Stdio.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void parseOne() {
        Stdio.print("==Testing parsing №1==\n");
        Expression exp = Expression.makeExp("(((y * x) / (5 + 8)) - (y + z))");
        exp.printExp();
        Stdio.newStr();
        try {
            int value = exp.eval("x = 10;y = 13;z=5");
            Stdio.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            Stdio.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void parseTwo() {
        Stdio.print("==Testing parsing №2==\n");
        Expression exp = Expression.makeExp("((5*x) + (6*y))");
        exp.printExp();
        Stdio.newStr();
        try {
            int value = exp.eval("x = 10; y = 13");
            Stdio.printf("Value: [%d]\n", value);
        } catch (ArgsErrors e) {
            Stdio.printf(ErrorsMessages.wrongFormat);
        }
        Stdio.newStr();
        Expression derX = exp.derivative("x");
        Stdio.print("DerX: ");
        derX.printExp();
        Stdio.newStr();
        Expression derY = exp.derivative("y");
        Stdio.print("DerY: ");
        derY.printExp();
        Stdio.newStr();
    }
}