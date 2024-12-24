package expression.generic;

public interface MultiExpression<T> {
    T evaluate(T x, T y, T z) throws DivisionByZero, OverflowException;
    T evaluate (T x) throws DivisionByZero, OverflowException;
}
