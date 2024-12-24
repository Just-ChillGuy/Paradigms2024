package expression;

import expression.exceptions.ExpressionParser;

import java.util.List;

public class Variable extends ExpressionImpl {
    private final String name;
    private final int index;
    public Variable(String name) {
        this.name = name;
        this.index = ExpressionParser.variables.indexOf(name);
    }

    public Variable(int index) {
        this.index = index;
        if (index >= ExpressionParser.variables.size()) {
            this.name = ExpressionParser.variables.get(0);
        } else {
            this.name = ExpressionParser.variables.get(index);
        }

    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (name.equals("x")) {
            return x;
        } else if (name.equals("y")) {
            return y;
        }
        return z;
    }

    @Override
    protected void toExpression(StringBuilder str) {
        str.append(name);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(index);
    }
}
