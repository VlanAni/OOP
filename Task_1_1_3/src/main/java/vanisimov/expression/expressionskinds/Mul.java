package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.InOut;

public class Mul extends Expression {

    private Expression firstOp;
    private Expression secondOp;

    public Mul(Expression firstOp, Expression secondOp) {
        this.firstOp = firstOp;
        this.secondOp = secondOp;
    }

    @Override
    public void printExp() {
        InOut.print("(");
        this.firstOp.printExp();
        InOut.print(" * ");
        this.secondOp.printExp();
        InOut.print(")");
    }

    @Override
    public Expression derivative(String var) {
        Mul leftDer = new Mul(this.firstOp.derivative(var), this.secondOp);
        Mul rightDer = new Mul(this.firstOp, this.secondOp.derivative(var));
        return new Add(leftDer, rightDer);
    }

    @Override
    public int eval(String values) {
        return this.firstOp.eval(values) * this.secondOp.eval(values);
    }
}