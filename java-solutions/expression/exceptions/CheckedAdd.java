package expression.exceptions;
import expression.Binary;
import expression.ExpressionImpl;

import java.util.List;

public class CheckedAdd extends Binary{
    public CheckedAdd(ExpressionImpl left, ExpressionImpl right) {
        super(left, right, "+");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int a = left.evaluate(x, y, z);
        int b = right.evaluate(x, y, z);
        if ((a > 0 && b > 0 && Integer.MAX_VALUE - b < a) ||
                (a < 0 && b < 0 && Integer.MIN_VALUE - b > a)) {
            throw new OverflowException("Overflow in Add");
        }
        return a + b;
    }
    @Override
    public int evaluate(List<Integer> variables) {
        int a = left.evaluate(variables);
        int b = right.evaluate(variables);
        if ((a > 0 && b > 0 && Integer.MAX_VALUE - b < a) ||
                (a < 0 && b < 0 && Integer.MIN_VALUE - b > a)) {
            throw new OverflowException("Overflow in Add");
        }
        return a + b;
    }
}
