package expression;

import java.util.List;

import static java.lang.Integer.numberOfLeadingZeros;
import static java.lang.Integer.numberOfTrailingZeros;

public class TrailingZero extends ExpressionImpl{
    private static final String operation = "t0";
    protected final ExpressionImpl expression;

    public TrailingZero(ExpressionImpl expression) {
        this.expression = expression;
    }

    @Override
    protected void toExpression(StringBuilder str) {
        str.append(operation);
        str.append("(");
        expression.toExpression(str);
        str.append(")");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return numberOfTrailingZeros(expression.evaluate(x, y, z));
    }
    @Override
    public int evaluate(List<Integer> variables) {
        return numberOfTrailingZeros(expression.evaluate(variables));
    }
}
