package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.StdOut;
import vanisimov.expression.exceptions.DivZero;
import vanisimov.expression.exceptions.ValErrors;

public class Number extends Expression {
    private int value;

    public Number(int number) {
        this.value = number;
    }

    @Override
    public void print() {
        if (this.value < 0) {
            StdOut.printf("(%d)", this.value);
        } else {
            StdOut.printf("%d", this.value);
        }
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public int eval(String values) throws ValErrors, DivZero {
        return this.value;
    }
}