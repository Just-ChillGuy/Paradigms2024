package expression;

import java.util.List;

public class And extends Binary{
    private final static String operation = "&";
    public And(ExpressionImpl left, ExpressionImpl right) {
        super(left, right, operation);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return left.evaluate(x, y, z) & right.evaluate(x, y, z);
    }
    @Override
    public int evaluate(List<Integer> variables) {
        return left.evaluate(variables) & right.evaluate(variables);
    }
}
