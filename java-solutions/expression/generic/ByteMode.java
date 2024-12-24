package expression.generic;

public class ByteMode implements GenericMode<Byte> {
    @Override
    public Byte add(Byte left, Byte right) throws OverflowException {
        return (byte) (left + right);
    }

    @Override
    public Byte subtract(Byte left, Byte right) throws OverflowException {
        return (byte) (left - right);
    }

    @Override
    public Byte multiply(Byte left, Byte right) throws OverflowException {
        return (byte) (left * right);
    }

    @Override
    public Byte divide(Byte left, Byte right) throws DivisionByZero, OverflowException {
        if (right == 0) {
            throw new DivisionByZero("Division by zero");
        }
        return (byte) (left / right);
    }

    @Override
    public Byte unaryMinus(Byte left) throws OverflowException {
        return (byte) - left;
    }

    @Override
    public Byte parse(String constant) {
        return (byte) Integer.parseInt(constant);
    }
}
