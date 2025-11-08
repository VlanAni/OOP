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
        Number num = new Number(125);
        Expression numDer = num.derivative("y");
        try {
            int val = num.eval("y = 12");

            assert (val == 125);

            int derVal = numDer.eval("x = 123412");

            assert (derVal == 0);

        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void varTest() {
        Variable var = new Variable("vovchik");
        Expression varDer = var.derivative("y");
        Expression varDer1 = var.derivative("vovchik");

        try {

            int varValue = var.eval("vovchik = -4213");

            assert (varValue == -4213);

            int vdVal = varDer.eval("");

            assert (vdVal == 0);

            vdVal = varDer1.eval("");

            assert (vdVal == 1);

        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        }
    }

    @Test
    void addTest() {
        Add addition = new Add(new Number(5), new Variable("x"));
        Expression derX = addition.derivative("x");
        Expression derY = addition.derivative("y");
        try {
            int value = addition.eval("x = 10");

            assert (value == 15);

            value = derX.eval("x = 10");

            assert (value == 1);

            value = derY.eval("x = 1235");

            assert (value == 0);

        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void subTest() {
        Sub sub = new Sub(new Variable("x"), new Number(-23));
        Expression derX = sub.derivative("x");
        Expression derY = sub.derivative("y");
        try {
            int value = sub.eval("x = 10");

            assert (value == 33);

            value = derX.eval("x = 1042");

            assert (value == 1);

            value = derY.eval("y = 12");

            assert (value == 0);

        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void mulTest() {
        Mul mul = new Mul(new Number(51), new Add(new Number(123), new Variable("x")));
        Expression derX = mul.derivative("x");
        Expression derY = mul.derivative("y");
        try {
            int value = mul.eval("x = 10");

            assert (value == 51 * (123 + 10));

            value = derX.eval("x = 321");

            assert (value == 51);

            value = derY.eval("y = 321");

            assert (value == 0);

        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void divTest() {
        Div div = new Div(new Number(51), new Add(new Number(2), new Variable("x")));
        Expression derX = div.derivative("x");
        try {

            int value = div.eval("x = 10");

            assert (value == 4);

            value = derX.eval("x = 3");

            assert (value == (-51 / 25));

        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void parseOne() {
        Expression exp = Expression.makeExp("(((y * x) / (5 + 8)) - (y + z))");
        try {
            int value = exp.eval("x = 10;y = 13  ; z=5     ");

            assert (value == 130 / 13 - 18);

        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }

    @Test
    void parseTwo() {
        Expression exp = Expression.makeExp("((5*x) + (6*y))");
        try {
            int value = exp.eval("x = 10; y = 13");

            assert (value == 50 + 78);

        } catch (ValErrors e) {
            StdOut.printf(ErrorsMessages.wrongFormat);
        } catch (DivZero e) {
            StdOut.printf(ErrorsMessages.zerdiv);
        }
    }
}