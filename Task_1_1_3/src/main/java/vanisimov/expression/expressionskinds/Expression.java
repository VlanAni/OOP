package vanisimov.expression.expressionskinds;

import vanisimov.expression.exceptions.ArgsErrors;

/**
 * Expression.
 */
public abstract class Expression {

    /**
     * print expression.
     */
    public abstract void printExp();

    /**
     * Taking derivative.
     *
     * @param var - variable.
     * @return - result of (exp)'.
     */
    public abstract Expression derivative(String var);

    /**
     * Calculate int value.
     *
     * @param values - values of variables.
     * @return - value of an expression.
     */
    public abstract int eval(String values) throws ArgsErrors;

    /**
     * Parse string expression.
     *
     * @param exp - string expression.
     * @return - parsed expression.
     */
    public static Expression makeExp(String exp) {
        ExpParser parser = new ExpParser(exp);
        parser.tokenize();
        return parser.parse();
    }
}