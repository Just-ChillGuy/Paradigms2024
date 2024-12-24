package expression.generic;

import expression.generic.GenericMode;

import java.util.List;

public class UnaryMinus<T> extends ExpressionImpl<T> {
    private static final String operation = "-";
    protected final ExpressionImpl<T> expression;
    protected final GenericMode<T> mode;

    public UnaryMinus(ExpressionImpl<T> expression, GenericMode<T> mode) {
        this.expression = expression;
        this.mode = mode;
    }

    @Override
    public void toExpression(StringBuilder str) {
        str.append(operation);
        str.append("(");
        expression.toExpression(str);
        str.append(")");
    }

    public T evaluate( T x, T y, T z) throws DivisionByZero, OverflowException {
        return mode.unaryMinus(expression.evaluate(x, y, z));
    }
    public T evaluate(T x) throws DivisionByZero, OverflowException {
        return mode.unaryMinus(expression.evaluate(x));
    }
}
