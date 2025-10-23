import org.junit.jupiter.api.Test;
import vanisimov.expression.customio.StdOut;
import vanisimov.expression.exceptions.DivZero;
import vanisimov.expression.exceptions.ValErrors;
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
        num.print();
        StdOut.newStr();
        numDer.print();
        StdOut.newStr();
        try {
            int val = num.eval("y = 12");
            StdOut.printf("Value: [%d]", val);
            StdOut.newStr();
        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void varTest() {
        StdOut.print("==Testing variable==\n");
        Variable var = new Variable("vovchik");
        var.print();
        StdOut.newStr();
        Expression varDer = var.derivative("y");
        varDer.print();
        StdOut.newStr();
        Expression varDer1 = var.derivative("vovchik");
        varDer1.print();
        StdOut.newStr();
    }

    @Test
    void addTest() {
        StdOut.print("==Testing Add class==\n");
        Add addition = new Add(new Number(5), new Variable("x"));
        addition.print();
        StdOut.newStr();
        Expression der = addition.derivative("x");
        der.print();
        StdOut.newStr();
        try {
            int value = addition.eval("x = 10");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void subTest() {
        StdOut.print("==Testing Sub class==\n");
        Sub sub = new Sub(new Number(123), new Number(-23));
        sub.print();
        StdOut.newStr();
        Expression der = sub.derivative("x");
        der.print();
        StdOut.newStr();
        try {
            int value = sub.eval("x = 10");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void mulTest() {
        StdOut.print("==Testing Mul class==\n");
        Mul mul = new Mul(new Number(51), new Add(new Number(123), new Variable("x")));
        mul.print();
        StdOut.newStr();
        Expression der = mul.derivative("x");
        der.print();
        StdOut.newStr();
        try {
            int value = mul.eval("x = 10");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void divTest() {
        StdOut.print("==Testing Div class==\n");
        Div div = new Div(new Number(51), new Add(new Number(123), new Variable("x")));
        div.print();
        StdOut.newStr();
        Expression der = div.derivative("x");
        der.print();
        StdOut.newStr();
        try {
            int value = div.eval("x = 10");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void parseOne() {
        StdOut.print("==Testing parsing №1==\n");
        Expression exp = Expression.makeExp("(((y * x) / (5 + 8)) - (y + z))");
        exp.print();
        StdOut.newStr();
        try {
            int value = exp.eval("x = 10;y = 13;z=5");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void parseTwo() {
        StdOut.print("==Testing parsing №2==\n");
        Expression exp = Expression.makeExp("((5*x) + (6*y))");
        exp.print();
        StdOut.newStr();
        try {
            int value = exp.eval("x = 10; y = 13");
            StdOut.printf("Value: [%d]\n", value);
        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
        StdOut.newStr();
        Expression derX = exp.derivative("x");
        StdOut.print("DerX: ");
        derX.print();
        StdOut.newStr();
        Expression derY = exp.derivative("y");
        StdOut.print("DerY: ");
        derY.print();
        StdOut.newStr();
    }
}