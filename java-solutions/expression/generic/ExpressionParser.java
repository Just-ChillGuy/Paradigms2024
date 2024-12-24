package expression.generic;

import expression.generic.GenericMode;

import java.util.*;


public class ExpressionParser<T>  {
    private static final Map<String, Integer> operations = Map.of(

            "-", 0,
            "+", 0,
            "*", 1,
            "/", 1,
            "(", 2,
            ")", 2,
            "_", 2);
    public static List<String> variables = List.of(
            "x", "y", "z");
    private static final Set<Character> binClasses = Set.of(
            '-', '+', '*', '/');
    private String expression;
    private final GenericMode<T> mode;

    public ExpressionParser (GenericMode<T> mode) {
        this.mode = mode;
    }


    public  MultiExpression<T> parse(String expression) throws Exception {
        this.expression = expression;
        ExpressionParser.variables = List.of(
                "x", "y", "z");
        return parse();
    }

    public MultiExpression<T> parse() throws Exception {
        List<String> splitExpr = new ArrayList<>();
        int cnt = 0;
        boolean isOpenBracket = true;
        //token splitting
        for (int i = 0; i < expression.length(); i++) {
            char currSymbol = expression.charAt(i);
            if (invalidToken(currSymbol)) {
                int start = i;
                while (i < expression.length() && !Character.isWhitespace(expression.charAt(i))) {
                    if (variables.contains(expression.substring(start, i))
                            || operations.containsKey(expression.substring(start, i))) {
                        break;
                    }
                    i++;
                }
                if (!variables.contains(expression.substring(start, i)) && !operations.containsKey(expression.substring(start, i))) {
                    throw new InvalidTokenException("Invalid token. Expected argument or operation, actual: " + expression.substring(start, i).charAt(0) + " <- " + splitExpr.size() + 1 + " token");
                } else {
                    splitExpr.add(expression.substring(start, i));
                }
                i--;
                cnt++;
            }
            if (Character.isWhitespace(currSymbol)) {
                continue;
            }
            if (Character.isDigit(currSymbol)) {
                if (cnt > 0 && Character.isDigit(splitExpr.get(cnt - 1).charAt(0))) {
                    throw new SpacesInNumbersException("No operation between arguments");
                }
                int start = i;
                while (i < expression.length()
                        && Character.isDigit(expression.charAt(i))) {
                    if (invalidToken(currSymbol)) {
                        throw new InvalidTokenException("Invalid token. Expected argument or operation, actual: " + currSymbol + " <- " + splitExpr.size() + 1 + " token");
                    }
                    i++;
                }
                splitExpr.add(expression.substring(start, i));
                cnt++;
                i--;
            }
            int end = i;
            while (end < expression.length() && !Character.isWhitespace(expression.charAt(end))
                    && !operations.containsKey(String.valueOf(expression.charAt(end)))) {
                end++;
            }

            if (operations.containsKey(String.valueOf(currSymbol))
                    || variables.contains(expression.substring(i, end))) {
                if (!splitExpr.isEmpty() && Objects.equals(splitExpr.get(cnt - 1), "(")
                        && currSymbol == ')') {
                    throw new EmptyParenthesesException("The absence of an expression in parentheses");
                }
                if ((isOpenBracket || i == 0) && currSymbol == '-') {
                    i++;
                    while (i < expression.length() && Character.isWhitespace(expression.charAt(i))) {
                        if (invalidToken(currSymbol)) {
                            throw new InvalidTokenException("Invalid token. Expected argument or operation, actual: " + currSymbol + " <- " + splitExpr.size() + 1 + " token");
                        }
                        i++;
                    }
                    if (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                        int start = i;
                        while (i < expression.length()
                                && Character.isDigit(expression.charAt(i))) {
                            if (invalidToken(currSymbol)) {
                                throw new InvalidTokenException("Invalid token. Expected argument or operation, actual: " + currSymbol + " <- " + splitExpr.size() + 1 + " token");
                            }
                            i++;
                        }
                        splitExpr.add("-" + expression.substring(start, i));
                        cnt++;
                        i--;
                    }
                    if (i >= expression.length()) {
                        throw new NoLastArgumentException("No last argument, expected: argument;   Actual: " + expression.charAt(i - 1));
                    }
                    end = i;
                    while (end < expression.length() && !Character.isWhitespace(expression.charAt(end))
                            && !operations.containsKey(String.valueOf(expression.charAt(end)))) {
                        end++;
                    }
                    if (variables.contains(expression.substring(i, end)) || operations.containsKey(String.valueOf(expression.charAt(i))) || operations.containsKey(expression.substring(i, end))) {
                        splitExpr.add("_");
                        cnt++;
                        i--;
                    }
                } else {
                    splitExpr.add(String.valueOf(currSymbol));
                    cnt++;
                }
            }
            if ((Objects.equals(splitExpr.get(splitExpr.size() - 1), "l0") || Objects.equals(splitExpr.get(splitExpr.size() - 1), "t0")) && (i < expression.length()
                    && (i < expression.length() - 1 && (variables.contains(String.valueOf(expression.charAt(i + 1))) || Character.isDigit(expression.charAt(i + 1)))))) {
                throw new InvalidTokenException("Expected Whitespace after " + splitExpr.get(splitExpr.size() - 1));

            }
            String added = splitExpr.get(splitExpr.size() - 1);
            isOpenBracket = (operations.containsKey(String.valueOf(added.charAt(0)))
                    && currSymbol != ')' && added.length() == 1) || added.equals("l0") || added.equals("t0");
        }
        //Converting an Expression to a Postfix Record
        List<String> stackOps = new ArrayList<>();
        List<String> postfix = new ArrayList<>();
        for (int i = splitExpr.size() - 1; i > -1; i--) {
            String s = splitExpr.get(i);
            String sPrev = "";
            if (i != splitExpr.size() - 1) {
                sPrev = splitExpr.get(i + 1);
            }
            boolean isUnaryOperation = Objects.equals(s, "l0") || Objects.equals(s, "t0");
            if (i != splitExpr.size() - 1 && (isUnaryOperation)
                    && binClasses.contains(sPrev.charAt(0)) && sPrev.length() == 1) {
                throw new NoLastArgumentException("No last argument, expected: argument;   Actual: " + s);
            }
            if (i == 0 && splitExpr.size() == 1 && (isUnaryOperation)) {
                throw new BareOperationException("Bare Operation, there are only '" + s + "' without operation");
            }
            if (i == splitExpr.size() - 1 && (isUnaryOperation)) {
                throw new NoLastArgumentException("No last argument, expected: argument;   Actual: " + s);
            }
            if (i == 0 && (binClasses.contains(s.charAt(0)) || s.charAt(0) == '_') && s.length() == 1
                    && splitExpr.size() == 1) {
                throw new BareOperationException("Bare Operation, there are only '" + s.charAt(0) + "' without operation");
            }
            if (i != 0 && (binClasses.contains(s.charAt(0))) && s.length() == 1
                    && Objects.equals(splitExpr.get(i - 1), "(")) {
                throw new NoFirstArgumentException("No first argument, expected: argument, actual:" + s.charAt(0));
            }
            if (i != splitExpr.size() - 1 && (binClasses.contains(s.charAt(0))) && s.length() == 1
                    && Objects.equals(splitExpr.get(i + 1), ")")) {
                throw new NoLastArgumentException("No last argument, expected: argument;   Actual: " + s.charAt(0));
            }
            if (i == splitExpr.size() - 1 && ((binClasses.contains(s.charAt(0)) || s.charAt(0) == '_')
                    && s.length() == 1)) {
                throw new NoLastArgumentException("No last argument, expected: argument;   Actual: " + s.charAt(0));
            }
            if (i != splitExpr.size() - 1 && (binClasses.contains(s.charAt(0)) || s.charAt(0) == '_') && s.length() == 1
                    && binClasses.contains(sPrev.charAt(0)) && sPrev.length() == 1) {
                throw new NoMiddleArgumentException("No middle argument, expected: argument;   Actual: " + sPrev.charAt(0));
            }
            if (i == 0 && binClasses.contains(s.charAt(0)) && s.length() == 1) {
                throw new NoFirstArgumentException("No first argument, expected: argument, actual:" + s.charAt(0));
            }
            if (variables.contains((s))) {
                postfix.add(s);
                continue;
            }
            if (Character.isDigit(s.charAt(0)) || (s.length() > 1 && Character.isDigit(s.charAt(1)))) {
                postfix.add(s);
                continue;
            }
            if (s.charAt(0) == '(') {
                try {
                    while (stackOps.get(stackOps.size() - 1).charAt(0) != ')') {
                        postfix.add(stackOps.remove(stackOps.size() - 1));
                    }
                } catch (java.lang.IndexOutOfBoundsException e) {
                    throw new NoClosingParentheses("No closing parenthesis");
                }
                stackOps.remove(stackOps.size() - 1);

                continue;
            }
            if (operations.containsKey(s)) {
                int prevPriority = stackOps.isEmpty() ? Integer.MIN_VALUE : operations.get(String.valueOf(stackOps.get(stackOps.size() - 1).charAt(0)));
                int currPriority = operations.get(String.valueOf(s.charAt(0)));
                while (prevPriority > currPriority && stackOps.get(stackOps.size() - 1).charAt(0) != ')') {
                    postfix.add(stackOps.remove(stackOps.size() - 1));
                    prevPriority = stackOps.isEmpty() ? Integer.MIN_VALUE : operations.get(String.valueOf(stackOps.get(stackOps.size() - 1).charAt(0)));
                }
                stackOps.add(s);
            }
        }
        while (!stackOps.isEmpty()) {
            postfix.add(stackOps.remove(stackOps.size() - 1));
        }
        return buildExpression(postfix);
    }
    // Evaluating an Expression
    private ExpressionImpl<T> buildExpression(List<String> postfix) throws OverflowException, NoOpeningParentheses {
        String element = postfix.remove(postfix.size() - 1);
        char trans = element.charAt(0);
        if (binClasses.contains(trans) && element.length() == 1) {
            ExpressionImpl<T> left = buildExpression(postfix);
            ExpressionImpl<T> right = buildExpression(postfix);
            switch (element.charAt(0)) {
                case '+':
                    return new Add<T>(left, right, mode);
                case '-':
                    return new Subtract<T>(left, right, mode);
                case '*':
                    return new Multiply<T>(left, right, mode);
                case '/':
                    return new Divide<T>(left, right, mode);
            }
        }
        if (Character.isDigit(trans) || (element.length() >= 2 && Character.isDigit(element.charAt(1)) && !variables.contains(element))
                && !operations.containsKey(element)) {
            try {
                return new Const<>(mode.parse(element));
            } catch (java.lang.NumberFormatException e) {
                if (trans == '-') {
                    throw new OverflowException("Constant < Integer min value" + " -> " + element);
                } else {
                    throw new OverflowException("Constant > Integer max value" + " -> " + element);
                }
            }
        }
        if (variables.contains(element)) {
            return new Variable<T>(element);
        }
        if (trans == '_') {
            return new UnaryMinus<T>(buildExpression(postfix), mode);
        }
        throw new NoOpeningParentheses("No opening parentheses");
    }
    private boolean invalidToken(char charExpression) {
        return !Character.isWhitespace(charExpression)
                && !Character.isDigit(charExpression)
                && !operations.containsKey(String.valueOf(charExpression))
                && !variables.contains(String.valueOf(charExpression));
    }
}
