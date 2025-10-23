package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.StdOut;
import vanisimov.expression.exceptions.DivZero;
import vanisimov.expression.exceptions.ValErrors;

public class Variable extends Expression {

    private String name;

    public Variable(String name) {
        this.name = name.trim();
    }

    @Override
    public void print() {
        StdOut.printf("%s", this.name);
    }

    @Override
    public Expression derivative(String var) {
        if (this.name.compareTo(var) == 0) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public int eval(String input) throws ValErrors, DivZero {
        String[] values = input.split(";");
        String[] data;
        int value = 0;
        if (values.length == 0) {
            throw new ValErrors();
        }
        for (String var : values) {
            data = var.split("=");
            if (data.length != 2) {
                throw new ValErrors();
            }
            data[0] = data[0].trim();
            data[1] = data[1].trim();
            if (this.name.compareTo(data[0]) == 0) {
                try {
                    value = Integer.parseInt(data[1]);
                    return value;
                } catch (NumberFormatException e) {
                    throw new ValErrors();
                }
            }
        }
        throw new ValErrors();
    }

}