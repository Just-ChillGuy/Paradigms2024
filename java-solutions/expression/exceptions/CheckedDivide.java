package expression.exceptions;
import expression.Binary;
import expression.ExpressionImpl;

import java.util.List;

public class CheckedDivide extends Binary{
    public CheckedDivide(ExpressionImpl left, ExpressionImpl right) {
        super(left, right, "/");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int a = left.evaluate(x, y, z);
        int b = right.evaluate(x, y, z);
        if (b == 0) {
            throw new DivisionByZero("Division by zero");
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("Overflow in Divide");
        }
        return left.evaluate(x, y, z) / right.evaluate(x, y, z);
    }
    @Override
    public int evaluate(List<Integer> variables) {
        int a = left.evaluate(variables);
        int b = right.evaluate(variables);
        if (b == 0) {
            throw new DivisionByZero("Division by zero");
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("Overflow in Divide");
        }
        return left.evaluate(variables) / right.evaluate(variables);
    }
}
