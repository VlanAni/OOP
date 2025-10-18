package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.StdOut;
import vanisimov.expression.exceptions.ArgsErrors;

public class Mul extends Expression {

    private Expression firstOp;
    private Expression secondOp;

    public Mul(Expression firstOp, Expression secondOp) {
        this.firstOp = firstOp;
        this.secondOp = secondOp;
    }

    @Override
    public void printExp() {
        StdOut.print("(");
        this.firstOp.printExp();
        StdOut.print(" * ");
        this.secondOp.printExp();
        StdOut.print(")");
    }

    @Override
    public Expression derivative(String var) {
        Mul leftDer = new Mul(this.firstOp.derivative(var), this.secondOp);
        Mul rightDer = new Mul(this.firstOp, this.secondOp.derivative(var));
        return new Add(leftDer, rightDer);
    }

    @Override
    public int eval(String values) throws ArgsErrors {
        return this.firstOp.eval(values) * this.secondOp.eval(values);
    }
}