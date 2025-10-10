package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.Stdio;
import vanisimov.expression.exceptions.ArgsErrors;

public class Div extends Expression {

    private Expression num;
    private Expression denom;

    public Div(Expression firstOp, Expression secondOp) {
        this.num = firstOp;
        this.denom = secondOp;
    }

    @Override
    public void printExp() {
        Stdio.print("(");
        this.num.printExp();
        Stdio.print(" / ");
        this.denom.printExp();
        Stdio.print(")");
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
    public int eval(String values) throws ArgsErrors  {
        return this.num.eval(values) / this.denom.eval(values);
    }
}