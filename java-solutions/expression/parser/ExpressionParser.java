package expression.parser;

import expression.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ExpressionParser implements TripleParser {
    private static final Map<Character, Integer> operations = Map.of(
            '|', 0,
        '^', 1,
        '&', 2,
        '-', 3,
        '+', 3,
        '*', 4,
        '/', 4,
        '(', 5,
        ')', 5,
        '_', 5);
    private static final Set<Character> variables = Set.of(
        'x', 'y', 'z');
    private static final Set<Character> binClasses = Set.of(
        '-', '+', '*', '/', '&', '^', '|');

    @Override
    public TripleExpression parse(String expression) {
        List<String> splitExpr = new ArrayList<>();
        boolean isOpenBracket = true;
        for (int i = 0; i < expression.length(); i++) {
            if (Character.isWhitespace(expression.charAt(i))) {
                continue;
            }
            if (Character.isDigit(expression.charAt(i))) {
                int start = i;
                while (i < expression.length()
                    && Character.isDigit(expression.charAt(i))) {
                    i++;
                }
                splitExpr.add(expression.substring(start, i));
                i--;
            }
            if (operations.containsKey(expression.charAt(i))
                || variables.contains(expression.charAt(i))) {
                if (isOpenBracket && expression.charAt(i) == '-') {
                    i++;
                    while (Character.isWhitespace(expression.charAt(i))) {
                        i++;
                    }
                    if (Character.isDigit(expression.charAt(i))) {
                        int start = i;
                        while (i < expression.length()
                            && Character.isDigit(expression.charAt(i))) {
                            i++;
                        }
                        splitExpr.add("-" + expression.substring(start, i));
                        i--;
                    }
                    if (variables.contains(expression.charAt(i)) || operations.containsKey(expression.charAt(i))) {
                        splitExpr.add("_");
                        i--;
                    }
                } else {
                    splitExpr.add(String.valueOf(expression.charAt(i)));
                }
            }
            String added = splitExpr.get(splitExpr.size() - 1);
            isOpenBracket = operations.containsKey(added.charAt(0))
                && expression.charAt(i) != ')' && added.length() == 1;
        }
        List<String> stackOps = new ArrayList<>();
        List<String> postfix = new ArrayList<>();
        for (int i = splitExpr.size() - 1; i > -1; i--) {
            String s = splitExpr.get(i);
            if (variables.contains(s.charAt(0))) {
                postfix.add(s);
                continue;
            }
            if (Character.isDigit(s.charAt(0)) || (s.length() > 1 && Character.isDigit(s.charAt(1)))) {
                postfix.add(s);
                continue;
            }
            if (s.charAt(0) == '(') {
                while (stackOps.get(stackOps.size() - 1).charAt(0) != ')') {
                    postfix.add(stackOps.remove(stackOps.size() - 1));
                }
                stackOps.remove(stackOps.size() - 1);
                continue;
            }
            if (operations.containsKey(s.charAt(0))) {
                int prevPriority = stackOps.isEmpty() ? Integer.MIN_VALUE : operations.get(stackOps.get(stackOps.size() - 1).charAt(0));
                int currPriority = operations.get(s.charAt(0));
                while (prevPriority > currPriority && stackOps.get(stackOps.size() - 1).charAt(0) != ')') {
                    postfix.add(stackOps.remove(stackOps.size() - 1));
                    prevPriority = stackOps.isEmpty() ? Integer.MIN_VALUE : operations.get(stackOps.get(stackOps.size() - 1).charAt(0));
                }
                stackOps.add(s);
                continue;
            }
        }
        while (!stackOps.isEmpty()) {
            postfix.add(stackOps.remove(stackOps.size() - 1));
        }
        return buildExpression(postfix);
    }

    private ExpressionImpl buildExpression(List<String> postfix) {
        String element = postfix.remove(postfix.size() - 1);
        char trans = element.charAt(0);
        if (binClasses.contains(trans) && element.length() == 1) {
            ExpressionImpl left = buildExpression(postfix);
            ExpressionImpl right = buildExpression(postfix);
            switch (element.charAt(0)) {
                case '|':
                    return new Or(left, right);
                case '^':
                    return new Xor(left, right);
                case '&':
                    return new And(left, right);
                case '+':
                    return new Add(left, right);
                case '-':
                    return new Subtract(left, right);
                case '*':
                    return new Multiply(left, right);
                case '/':
                    return new Divide(left, right);
            }
        }
        if (Character.isDigit(trans) || (element.length() >= 2 && Character.isDigit(element.charAt(1)))) {
            return new Const(Integer.parseInt(element));
        }
        if (variables.contains(trans)) {
            return new Variable(element);
        }
        if (trans == '_') {
            return new UnaryMinus(buildExpression(postfix));
        }
        throw new RuntimeException();
    }
}
