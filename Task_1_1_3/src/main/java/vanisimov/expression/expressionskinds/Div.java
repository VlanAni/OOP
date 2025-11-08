package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.StdOut;
import vanisimov.expression.exceptions.DivZero;
import vanisimov.expression.exceptions.ValErrors;

public class Div extends Expression {

    private Expression num;
    private Expression denom;

    public Div(Expression firstOp, Expression secondOp) {
        this.num = firstOp;
        this.denom = secondOp;
    }

    @Override
    public void print() {
        StdOut.print("(");
        this.num.print();
        StdOut.print(" / ");
        this.denom.print();
        StdOut.print(")");
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
    public int eval(String values) throws ValErrors, DivZero {
        int arg2 = this.denom.eval(values);
        if (arg2 == 0) {
            throw new DivZero();
        }
        int arg1 = this.num.eval(values);
        return arg1 / arg2;
    }
}