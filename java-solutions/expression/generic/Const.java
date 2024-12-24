package expression.generic;

import java.util.List;

public class Const<T> extends ExpressionImpl<T> {
    private final T constant;
    public Const (T constant) {
        this.constant = constant;
    }

    public T evaluate(T x, T y, T z) {
        return this.constant;
    }
    public T evaluate(T x) {
        return this.constant;
    }

    @Override
    public void toExpression(StringBuilder str) {
        str.append(constant);
    }
}