package expression.exceptions;
import expression.ExpressionImpl;
import expression.UnaryMinus;

import java.util.List;

public class CheckedNegate extends UnaryMinus{
    public CheckedNegate(ExpressionImpl expression) {
        super(expression);
    }
    public int evaluate(int x, int y, int z) {
        if (expression.evaluate(x, y, z) == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow in UnaryMinus");
        }
        return -expression.evaluate(x, y, z);
    }
    public int evaluate(List<Integer> variables) {
        if (expression.evaluate(variables) == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow in UnaryMinus");
        }
        return -expression.evaluate(variables);
    }
}
