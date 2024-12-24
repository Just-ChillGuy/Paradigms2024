package expression.generic;

import expression.exceptions.ExpressionParser;

import java.util.List;

public class Variable<T> extends ExpressionImpl<T> {
    private final String name;
    public Variable(String name) {
        this.name = name;
    }


    public T evaluate(T x, T y, T z) {
        if (name.equals("x")) {
            return x;
        } else if (name.equals("y")) {
            return y;
        }
        return z;
    }
    public T evaluate(T x) {
        return x;
    }

    @Override
    public void toExpression(StringBuilder str) {
        str.append(name);
    }

}
