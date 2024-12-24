package expression.generic;

public class Add<T> extends Binary<T> {
    private final static String operation = "+";
    public Add(ExpressionImpl<T> left, ExpressionImpl<T> right, GenericMode<T> mode) {
        super(left, right, operation, mode);
    }


    public T evaluate(T x ,T y, T z) throws DivisionByZero, OverflowException {
        return mode.add(left.evaluate(x, y, z), right.evaluate(x, y, z));

    }

    public T evaluate(T x) throws DivisionByZero, OverflowException {
        return mode.add(left.evaluate(x), right.evaluate(x));
    }
}
