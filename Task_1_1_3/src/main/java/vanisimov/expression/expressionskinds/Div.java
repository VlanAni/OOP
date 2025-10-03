package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.InOut;

public class Div extends Expression {

    private Expression num;
    private Expression denom;

    public Div(Expression firstOp, Expression secondOp) {
        this.num = firstOp;
        this.denom = secondOp;
    }

    @Override
    public void printExp() {
        InOut.print("(");
        this.num.printExp();
        InOut.print(" / ");
        this.denom.printExp();
        InOut.print(")");
    }

    @Override
    public Expression derivative(String var) {
        Mul derNumLeft = new Mul(this.num.derivative(var), this.denom);
        Mul derNumRight = new Mul(this.num, this.denom.derivative(var));
        Sub derNum = new Sub(derNumLeft, derNumRight);
        Mul derDenom = new Mul(this.denom, this.denom);
        return new Div(derNum, derDenom);
    }

    @Override
    public int eval(String values) {
        return this.num.eval(values) / this.denom.eval(values);
    }
}