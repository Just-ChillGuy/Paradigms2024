package expression.generic;

public abstract class ExpressionImpl<T> implements MultiExpression<T> {
    public abstract void toExpression(StringBuilder str);

    @Override
    public String toString() {
        StringBuilder strExpression = new StringBuilder();
        toExpression(strExpression);
        return strExpression.toString();
    }
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof expression.generic.ExpressionImpl<?> expr) {
            return (expr.equals(this.toString()));
        }
        return false;
    }
}
