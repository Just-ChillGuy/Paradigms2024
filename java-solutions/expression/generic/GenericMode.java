package expression.generic;

public interface GenericMode<T> {
    T add(T left, T right) throws OverflowException;
    T subtract(T left, T right) throws OverflowException;
    T multiply(T left, T right) throws OverflowException;
    T divide(T left, T right) throws DivisionByZero, OverflowException;
    T unaryMinus(T left) throws OverflowException;
    T parse(String constant);
}
