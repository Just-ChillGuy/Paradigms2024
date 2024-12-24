function Const(value) {
    this.value = Number(value);
}

Const.prototype.prefix = function() {
    return this.value.toString();
};

Const.prototype.evaluate = function() {
    return this.value;
};

Const.prototype.toString = function() {
    return this.value.toString();
};

function Variable(name) {
    this.name = name;
}

Variable.prototype.prefix = function() {
    return this.name;
};

Variable.prototype.evaluate = function(...args) {
    return args['xyz'.indexOf(this.name)];
};

Variable.prototype.toString = function() {
    return this.name;
};

function Operation(...args) {
    this.args = args;
}

Operation.prototype.toString = function() {
    return this.args.join(' ') + ' ' + this.operator;
};
function Operation(operator, ...args) {
    this.operator = operator;
    this.args = args;
}

Operation.prototype.toString = function() {
    return this.args.join(' ') + ' ' + this.operator;
};

Operation.prototype.prefix = function() {
    return "(" + this.operator + " " + this.args.map(a => a.prefix()).join(" ") + ")";
};


function OperationClass(operator, evaluateFunction) {
    function MyOperation(...args) {
        Operation.call(this, operator, ...args);
    }

    MyOperation.prototype = Object.create(Operation.prototype);
    MyOperation.prototype.constructor = MyOperation;

    MyOperation.prototype.evaluate = evaluateFunction;

    return MyOperation;
}

// :NOTE: copy-paste
const Add = OperationClass("+", function(...args) {
    return this.args.reduce((a, b) => a.evaluate(...args) + b.evaluate(...args));
});

const Subtract = OperationClass("-", function(...args) {
    return this.args.reduce((a, b) => a.evaluate(...args) - b.evaluate(...args));
});

const Multiply = OperationClass("*", function(...args) {
    return this.args.reduce((a, b) => a.evaluate(...args) * b.evaluate(...args));
});

const Divide = OperationClass("/", function(...args) {
    return this.args.reduce((a, b) => a.evaluate(...args) / b.evaluate(...args));
});

const Negate = OperationClass("negate", function(...args) {
    return -this.args[0].evaluate(...args);
});

const Sin = OperationClass("sin", function(...args) {
    return Math.sin(this.args[0].evaluate(...args));
});

const Cos = OperationClass("cos", function(...args) {
    return Math.cos(this.args[0].evaluate(...args));
});

const Mean = OperationClass("mean", function(...args) {
    return this.args.reduce((acc, val) => acc + val.evaluate(...args), 0) / this.args.length;
});

const Var = OperationClass("var", function(...args) {
    const mean = this.args.reduce((acc, val) => acc + val.evaluate(...args), 0) / this.args.length;
    const variance = this.args.reduce((acc, val) => acc + Math.pow(val.evaluate(...args) - mean, 2), 0) / this.args.length;
    // Math.pow(val.evaluate(...args) - mean, 2) у меня работает быстрее на 3 - 4 сек,
    // нежели просто (val.evaluate(...args) - mean) * (val.evaluate(...args) - mean), поэтому оставил его
    return variance;
});


const mapOp = {
    "+": Add,
    "-": Subtract,
    "/": Divide,
    "*": Multiply,
    "negate": Negate,
    "sin": Sin,
    "cos": Cos
}

const MAP_VARIABLES = {
    "x": new Variable("x"),
    "y": new Variable("y"),
    "z": new Variable("z")
}

function parse(expression) {
    const tokens = expression.trim().split(/\s+/);//.filter(s => s);
    const stack = [];
    for (const token of tokens) {
        if (!isNaN(Number(token))) { stack.push(new Const(Number(token))); }
        // :NOTE: support more variable names
        else if (token.match(/^[xyz]$/)) { stack.push(new Variable(token)); }
        else {
            let right = stack.pop();
            let left
            if (token !== 'negate' && token !== 'sin' && token !== 'cos') {
                left = stack.pop();
                stack.push(new mapOp[token](left, right));
            } else {
                stack.push(new mapOp[token](right));
            }
        }
    }
    if (stack.length !== 1) {
        if (!stack.every(element => element instanceof Const)) {
            throw new Error("Invalid expression: unbalanced operators");
        }
    }
    return stack.pop();
}

function ParsingError(message) {
    this.message = message;
    Error.call(this, message);
}
ParsingError.prototype = Object.create(Error.prototype);
ParsingError.prototype.name = "ParsingError";
ParsingError.prototype.constructor = ParsingError;

let index = 0;

const MAP_BINARY = {
    "+": Add,
    "-": Subtract,
    "/": Divide,
    "*": Multiply
}

const MAP_UNARY = {
    "negate": Negate
}

const MAP_MULTY = {
    "mean": Mean,
    "var": Var
}

function parsePrefix(expression) {
    if (expression.length === 0) {
        throw new ParsingError("Empty input");
    }
    index = 0;
    expression = expression.replaceAll("(", " ( ");
    expression = expression.replaceAll(")", " ) ");
    const tokens = expression.trim().split(/\s+/);
    if (tokens.length === 0) {
        throw new ParsingError("No tokens");
    }
    let result;
    try {
      result = parsing(tokens);
    } catch(e) {
        throw e;
    }
    if (index !== tokens.length) {
        throw new ParsingError("Missing operation");
    }
    return result;
}

function parsing(tokens) {
    if (tokens[index] in MAP_VARIABLES) {
        return MAP_VARIABLES[tokens[index++]];
    } else if (!isNaN(tokens[index])) {
        return new Const(Number(tokens[index++]));
    } else if (tokens[index] === "(") {
        index++;
        let operationResult;
        if (tokens[index] in MAP_UNARY) {
            const unaryOp = MAP_UNARY[tokens[index++]];
            const variable = parsing(tokens);
            operationResult = new unaryOp(variable);
        } else if (tokens[index] in MAP_BINARY) {
            const binaryOp = MAP_BINARY[tokens[index++]];
            let variables = [];
            for (let i = 0; i < 2; i++) {
                variables.push(parsing(tokens));
            }
            operationResult = new binaryOp(...variables);
        } else if (tokens[index] in MAP_MULTY) {
            const multyOp = MAP_MULTY[tokens[index++]];
            let variables = [];
            while (tokens[index] !== ")") {
                variables.push(parsing(tokens));
            }

            operationResult = new multyOp(...variables);
        } else {
            throw new ParsingError("No such operation");
        }
        if (tokens[index++] === ")") {
            return operationResult;
        } else {
            throw new ParsingError("Miss close bracket");
        }
    } else {
        throw new ParsingError("Unknown token");
    }
}





//  let expr = new Negate(new Variable('x'));
// expr = parsePrefix("(mean x y z 3 5)");
// console.log(expr.evaluate(0, 0, 0));
// console.log(expr.prefix());
// console.log(expr.toString());
// //
// console.log(new Const(10).evaluate(1, 1, 1))// console.log(expr.simplify().toString());
//
//
// let expr2 = parse("x x 2 - * 1 +");
// let diffExpr = expr2.diff("x");
// let simplifiedDiffExpr = diffExpr.simplify();
// console.log(simplifiedDiffExpr.toString());