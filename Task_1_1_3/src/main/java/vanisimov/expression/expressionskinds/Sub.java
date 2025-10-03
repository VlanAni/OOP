package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.InOut;

public class Sub extends Expression {

    private Expression firstOp;
    private Expression secondOp;

    public Sub(Expression firstOp, Expression secondOp) {
        this.firstOp = firstOp;
        this.secondOp = secondOp;
    }

    @Override
    public void printExp() {
        InOut.print("(");
        this.firstOp.printExp();
        InOut.print(" - ");
        this.secondOp.printExp();
        InOut.print(")");
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(this.firstOp.derivative(var), this.secondOp.derivative(var));
    }

    @Override
    public int eval(String values) {
        return this.firstOp.eval(values) - this.secondOp.eval(values);
    }
}