package expression.generic;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private static final Map<String, GenericMode<?>> modes = Map.of("i", new IntMode(),
            "d", new DoubleMode(),
            "bi", new BigIntegerMode(),
            "u", new UncheckedIntMode(),
            "b", new ByteMode());
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        GenericMode<?> type = modes.get(mode);
        if (type == null) {
            throw new IllegalArgumentException("No such mode: " + mode);
        }
        return creation(type, expression, x1, x2, y1, y2, z1, z2);
    }
    private <T> Object[][][] creation(GenericMode<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] arr = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExpressionParser<T> express = new ExpressionParser<>(type);
        MultiExpression<T> expr =  express.parse(expression);

        for (int i = 0; i < x2 - x1 + 1; i++) {
            for (int y = 0; y < y2 - y1 + 1; y++) {
                for (int z = 0; z < z2 - z1 + 1; z++) {
                    try {
                        arr[i][y][z] = expr.evaluate(type.parse(Integer.toString(x1 + i)),
                                type.parse(Integer.toString(y1 + y)), type.parse(Integer.toString(z1 + z)));
                    } catch (ExtraneousErrors e) {
                        arr[i][y][z] = null;
                    }
                }
            }
        }
        return arr;
    }
}
