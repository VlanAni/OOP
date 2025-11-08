package vanisimov.expression.expressionskinds;

import vanisimov.expression.customio.StdOut;
import vanisimov.expression.exceptions.DivZero;
import vanisimov.expression.exceptions.ValErrors;

/**
 * Addition.
 */
public class Add extends Expression {

    private Expression firstOp;
    private Expression secondOp;

    /**
     * Same.
     *
     * @param firstOp - first operand.
     * @param secondOp - second operand.
     */
    public Add(Expression firstOp, Expression secondOp) {
        this.firstOp = firstOp;
        this.secondOp = secondOp;
    }

    /**
     * same.
     */
    @Override
    public void print() {
        StdOut.print("(");
        this.firstOp.print();
        StdOut.print(" + ");
        this.secondOp.print();
        StdOut.print(")");
    }

    /**
     * same.
     *
     * @param var - same.
     * @return - same.
     */
    @Override
    public Expression derivative(String var) {
        return new Add(this.firstOp.derivative(var), this.secondOp.derivative(var));
    }

    /**
     * same.
     *
     * @param values - values of variables.
     * @return - same.
     */
    @Override
    public int eval(String values) throws ValErrors, DivZero {
        return this.firstOp.eval(values) + this.secondOp.eval(values);
    }
}