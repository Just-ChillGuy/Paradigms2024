package expression;

import java.util.List;

import static java.lang.Integer.numberOfLeadingZeros;

public class LeadingZero extends ExpressionImpl{
    private static final String operation = "l0";
    protected final ExpressionImpl expression;

    public LeadingZero(ExpressionImpl expression) {
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
        return numberOfLeadingZeros(expression.evaluate(x, y, z));
    }
    @Override
    public int evaluate(List<Integer> variables) {
        return numberOfLeadingZeros(expression.evaluate(variables));
    }
}
