package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.StdOut;
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
        StdOut.print("(");
        this.firstOp.printExp();
        StdOut.print(" - ");
        this.secondOp.printExp();
        StdOut.print(")");
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