package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.StdIO;
import vanisimov.expression.exceptions.ArgsErrors;

public class Sub extends Expression {

    private Expression firstOp;
    private Expression secondOp;

    public Sub(Expression firstOp, Expression secondOp) {
        this.firstOp = firstOp;
        this.secondOp = secondOp;
    }

    @Override
    public void printExp() {
        StdIO.print("(");
        this.firstOp.printExp();
        StdIO.print(" - ");
        this.secondOp.printExp();
        StdIO.print(")");
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(this.firstOp.derivative(var), this.secondOp.derivative(var));
    }

    @Override
    public int eval(String values) throws ArgsErrors {
        return this.firstOp.eval(values) - this.secondOp.eval(values);
    }
}