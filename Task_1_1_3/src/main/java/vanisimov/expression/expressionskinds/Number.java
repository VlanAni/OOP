package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.Stdio;

public class Number extends Expression {
    private int value;

    public Number(int number) {
        this.value = number;
    }

    @Override
    public void printExp() {
        Stdio.printf("%d", this.value);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public int eval(String values) {
        return this.value;
    }
}