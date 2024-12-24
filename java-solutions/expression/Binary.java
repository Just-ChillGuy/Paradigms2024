package expression;

public abstract class Binary extends ExpressionImpl{
    protected final ExpressionImpl left;
    protected final ExpressionImpl right;

    protected final String operation;

    protected Binary(ExpressionImpl left, ExpressionImpl right, String operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    protected void toExpression(StringBuilder str) {
        str.append("(");
        left.toExpression(str);
        str.append(' ').append(operation).append(' ');
        right.toExpression(str);
        str.append(")");
    }
}
