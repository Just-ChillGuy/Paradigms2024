package expression.generic;

public class UncheckedIntMode implements GenericMode<Integer> {
    @Override
    public Integer add(Integer left, Integer right) throws OverflowException {
        return left + right;
    }

    @Override
    public Integer subtract(Integer left, Integer right) throws OverflowException {
        return left - right;
    }

    @Override
    public Integer multiply(Integer left, Integer right) throws OverflowException {
        return left * right;
    }

    @Override
    public Integer divide(Integer left, Integer right) throws DivisionByZero, OverflowException {
        if (right == 0) {
            throw new DivisionByZero("Division by zero");
        }
        return left / right;
    }

    @Override
    public Integer unaryMinus(Integer left) throws OverflowException {
        return - left;
    }

    @Override
    public Integer parse(String constant) {
        return Integer.parseInt(constant);
    }
}
