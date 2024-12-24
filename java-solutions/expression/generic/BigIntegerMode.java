package expression.generic;

import java.math.BigInteger;

public class BigIntegerMode implements GenericMode<BigInteger> {
    @Override
    public BigInteger add(BigInteger left, BigInteger right) {
        return left.add(right);
    }

    @Override
    public BigInteger subtract(BigInteger left, BigInteger right) {
        return left.subtract(right);
    }

    @Override
    public BigInteger multiply(BigInteger left, BigInteger right) {
        return left.multiply(right);
    }

    @Override
    public BigInteger divide(BigInteger left, BigInteger right) throws DivisionByZero {
        if (right.equals(BigInteger.ZERO)) {
            throw new DivisionByZero("Division by zero");
        }
        return left.divide(right);
    }

    @Override
    public BigInteger unaryMinus(BigInteger left) {
        return left.negate();
    }

    @Override
    public BigInteger parse(String constant) {
        return new BigInteger(constant);
    }

}
