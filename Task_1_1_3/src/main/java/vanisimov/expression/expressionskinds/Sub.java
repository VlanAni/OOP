package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.StdOut;
import vanisimov.expression.exceptions.DivZero;
import vanisimov.expression.exceptions.ValErrors;

public class Sub extends Expression {

    private Expression firstOp;
    private Expression secondOp;

    public Sub(Expression firstOp, Expression secondOp) {
        this.firstOp = firstOp;
        this.secondOp = secondOp;
    }

    @Override
    public void print() {
        StdOut.print("(");
        this.firstOp.print();
        StdOut.print(" - ");
        this.secondOp.print();
        StdOut.print(")");
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(this.firstOp.derivative(var), this.secondOp.derivative(var));
    }

    @Override
    public int eval(String values) throws ValErrors, DivZero {
        return this.firstOp.eval(values) - this.secondOp.eval(values);
    }
}