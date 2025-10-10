package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.Stdio;
import vanisimov.expression.exceptions.ArgsErrors;
import vanisimov.expression.exceptions.ErrorsMessages;

public class Variable extends Expression {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void printExp() {
        Stdio.printf("%s", this.name);
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
    public int eval(String input) throws ArgsErrors {
        String[] values = input.split(";");
        String[] data;
        int value = 0;
        if (values.length == 0) {
            throw new ArgsErrors(ErrorsMessages.noValues);
        }
        for (String var : values) {
            data = var.split("=");
            if (data.length != 2) {
                throw new ArgsErrors(ErrorsMessages.formatErr);
            }
            data[0] = data[0].trim();
            data[1] = data[1].trim();
            if (this.name.compareTo(data[0]) == 0) {
                value = Integer.parseInt(data[1]);
                return value;
            }
        }
        throw new ArgsErrors(ErrorsMessages.noVar);
    }

}