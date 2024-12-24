package expression.generic;

import java.math.BigInteger;

public class DoubleMode implements GenericMode<Double> {
    @Override
    public Double add(Double left, Double right) {
        return left + right;
    }

    @Override
    public Double subtract(Double left, Double right) {
        return left - right;
    }

    @Override
    public Double multiply(Double left, Double right) {
        return left * right;
    }

    @Override
    public Double divide(Double left, Double right) {
        return left / right;
    }

    @Override
    public Double unaryMinus(Double left) {
        return - left;
    }

    @Override
    public Double parse(String constant) {
        return Double.parseDouble(constant);
    }
}
