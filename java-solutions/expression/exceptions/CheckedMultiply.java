package expression.exceptions;
import expression.Binary;
import expression.ExpressionImpl;

import java.util.List;

public class CheckedMultiply extends Binary{
    public CheckedMultiply(ExpressionImpl left, ExpressionImpl right) {
        super(left, right, "*");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int a = left.evaluate(x, y, z);
        int b = right.evaluate(x, y, z);

        if (a > 0 && b > 0 && a > Integer.MAX_VALUE / b || a > 0 && b < 0 && b < Integer.MIN_VALUE / a
                || a < 0 && b > 0 && a < Integer.MIN_VALUE / b || a < 0 && b < 0
                && (-a) > Integer.MAX_VALUE / (-b)) {
            throw new OverflowException("Overflow in Multiply");
        }
        if (a == Integer.MIN_VALUE && b != 1 && b != 0 || b == Integer.MIN_VALUE && a != 1
                && a != 0) {
            throw new OverflowException("Overflow in Multiply");
        }
        return left.evaluate(x, y, z) * right.evaluate(x, y, z);
    }
    @Override
    public int evaluate(List<Integer> variables) {
        int a = left.evaluate(variables);
        int b = right.evaluate(variables);

        if (a > 0 && b > 0 && a > Integer.MAX_VALUE / b || a > 0 && b < 0 && b < Integer.MIN_VALUE / a
                || a < 0 && b > 0 && a < Integer.MIN_VALUE / b || a < 0 && b < 0
                && (-a) > Integer.MAX_VALUE / (-b)) {
            throw new OverflowException("Overflow in Multiply");
        }
        if (a == Integer.MIN_VALUE && b != 1 && b != 0 || b == Integer.MIN_VALUE && a != 1
                && a != 0) {
            throw new OverflowException("Overflow in Multiply");
        }
        return left.evaluate(variables) * right.evaluate(variables);
    }
}
