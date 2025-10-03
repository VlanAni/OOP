import org.junit.jupiter.api.Test;
import vanisimov.expression.expressionskinds.Number;
import vanisimov.expression.expressionskinds.Expression;
import vanisimov.expression.expressionskinds.Variable;
import vanisimov.expression.customio.InOut;
import vanisimov.expression.expressionskinds.Add;
import vanisimov.expression.expressionskinds.Sub;
import vanisimov.expression.expressionskinds.Mul;
import vanisimov.expression.expressionskinds.Div;

class ExpressionTest {

    @Test
    void numberTest() {
        InOut.print("==Testing Number==\n");
        Number num = new Number(125);
        Expression numDer = num.derivative("y");
        num.printExp();
        InOut.newStr();
        numDer.printExp();
        InOut.newStr();
        int val = num.eval("y = 12");
        InOut.printf("Value: [%d]", val);
        InOut.newStr();
    }

    @Test
    void varTest() {
        InOut.print("==Testing variable==\n");
        Variable var = new Variable("vovchik");
        Expression varDer = var.derivative("y");
        Expression varDer1 = var.derivative("vovchik");
        var.printExp();
        InOut.newStr();
        varDer.printExp();
        InOut.newStr();
        varDer1.printExp();
        InOut.newStr();
    }

    @Test
    void addTest() {
        InOut.print("==Testing Add class==\n");
        Add addition = new Add(new Number(5), new Variable("x"));
        addition.printExp();
        InOut.newStr();
        Expression der = addition.derivative("x");
        der.printExp();
        InOut.newStr();
        int value = addition.eval("x = 10");
        InOut.printf("Value: [%d]\n", value);
    }

    @Test
    void subTest() {
        InOut.print("==Testing Sub class==\n");
        Sub sub = new Sub(new Number(123), new Number(-23));
        sub.printExp();
        InOut.newStr();
        Expression der = sub.derivative("x");
        der.printExp();
        InOut.newStr();
        int value = sub.eval("x = 10");
        InOut.printf("Value: [%d]\n", value);
    }

    @Test
    void mulTest() {
        InOut.print("==Testing Mul class==\n");
        Mul mul = new Mul(new Number(51), new Add(new Number(123), new Variable("x")));
        mul.printExp();
        InOut.newStr();
        Expression der = mul.derivative("x");
        der.printExp();
        InOut.newStr();
        int value = mul.eval("x = 25");
        InOut.printf("Value: [%d]\n", value);
    }

    @Test
    void divTest() {
        InOut.print("==Testing Div class==\n");
        Div div = new Div(new Number(51), new Add(new Number(123), new Variable("x")));
        div.printExp();
        InOut.newStr();
        Expression der = div.derivative("x");
        der.printExp();
        InOut.newStr();
        int value = div.eval("x = -122");
        InOut.printf("Value: [%d]\n", value);
    }

    @Test
    void parseOne() {
        InOut.print("==Testing Exp (1)==\n");
        Expression exp = Expression.makeExp("(((y * x) / (5 + 8)) - (y + z))");
        exp.printExp();
        int value = exp.eval("x = 5;y = 10;z = 18");
        InOut.newStr();
        InOut.printf("Value: [%d]\n", value);
    }

    @Test
    void parseTwo() {
        InOut.print("==Testing Exp (1)==\n");
        Expression exp = Expression.makeExp("((5*x) + (6*y))");
        exp.printExp();
        int value = exp.eval("x = 5;y = 10;z = 18");
        InOut.newStr();
        InOut.printf("Value: [%d]\n", value);
        Expression derX = exp.derivative("x");
        InOut.print("DerX: ");
        derX.printExp();
        InOut.newStr();
        Expression derY = exp.derivative("y");
        InOut.print("DerY: ");
        derY.printExp();
    }
}