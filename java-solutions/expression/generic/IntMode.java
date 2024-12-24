package expression.generic;

public class IntMode implements GenericMode<Integer> {
    @Override
    public Integer add(Integer left, Integer right) throws OverflowException {
        if ((left > 0 && right > 0 && Integer.MAX_VALUE - right < left) ||
                (left < 0 && right < 0 && Integer.MIN_VALUE - right > left)) {
            throw new expression.generic.OverflowException("Overflow in Add");
        }
        return left + right;
    }

    @Override
    public Integer subtract(Integer left, Integer right) throws OverflowException {
        if ((left <= 0 && right >= 0 && Integer.MIN_VALUE + right > left) ||
                (left >= 0 && right <= 0 && Integer.MAX_VALUE + right < left)) {
            throw new OverflowException("Overflow in Subtract");
        }
        return left - right;
    }

    @Override
    public Integer multiply(Integer left, Integer right) throws OverflowException {
        if (left > 0 && right > 0 && left > Integer.MAX_VALUE / right || left > 0 && right < 0 && right < Integer.MIN_VALUE / left
                || left < 0 && right > 0 && left < Integer.MIN_VALUE / right || left < 0 && right < 0
                && (-left) > Integer.MAX_VALUE / (-right)) {
            throw new OverflowException("Overflow in Multiply");
        }
        if (left == Integer.MIN_VALUE && right != 1 && right != 0 || right == Integer.MIN_VALUE && left != 1
                && left != 0) {
            throw new OverflowException("Overflow in Multiply");
        }
        return left * right;
    }

    @Override
    public Integer divide(Integer left, Integer right) throws DivisionByZero, OverflowException {
        if (right == 0) {
            throw new DivisionByZero("Division by zero");
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new OverflowException("Overflow in Divide");
        }
        return left / right;
    }

    @Override
    public Integer unaryMinus(Integer left) throws OverflowException {
        if (left == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow in UnaryMinus");
        }
        return - left;
    }

    @Override
    public Integer parse(String constant) {
        return Integer.parseInt(constant);
    }
}
