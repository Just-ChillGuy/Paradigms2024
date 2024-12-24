package expression.generic;

import expression.generic.GenericMode;

public class Divide<T> extends Binary<T> {
    private final static String operation = "/";
    public Divide(ExpressionImpl<T> left, ExpressionImpl<T> right, GenericMode<T> mode) {
        super(left, right, operation, mode);
    }


    public T evaluate(T x ,T y, T z) throws DivisionByZero, OverflowException {
        return mode.divide(left.evaluate(x, y, z), right.evaluate(x, y, z));

    }

    public T evaluate(T x) throws DivisionByZero, OverflowException {
        return mode.divide(left.evaluate(x), right.evaluate(x));
    }
}
