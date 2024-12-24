package expression.generic;

import expression.generic.GenericMode;

public abstract class Binary<T> extends ExpressionImpl<T> {
    protected final ExpressionImpl<T> left;
    protected final ExpressionImpl<T> right;

    protected final String operation;
    protected final GenericMode<T> mode;

    protected Binary(ExpressionImpl<T> left, ExpressionImpl<T> right, String operation, GenericMode<T> mode) {
        this.left = left;
        this.right = right;
        this.operation = operation;
        this.mode = mode;
    }


    @Override
    public void toExpression(StringBuilder str) {
        str.append("(");
        left.toExpression(str);
        str.append(' ').append(operation).append(' ');
        right.toExpression(str);
        str.append(")");
    }
}
