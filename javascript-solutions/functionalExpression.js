"use strict";

const variables = ['x', 'y', 'z'];

const cnst = (value) => () => value;
const variable = (name) => (...args) => args[variables.indexOf(name)];

const binaryOperation = (operation) => (left, right) => (...args) => operation(left(...args), right(...args));
const unaryOperation = (operation) => (left) => (...args) => operation(left(...args));

const negate = unaryOperation(left => -left);
const square = unaryOperation(left => left * left);
const sqrt = unaryOperation(left => Math.sqrt(Math.abs(left)));

const add = binaryOperation((left, right) => (left + right));
const subtract = binaryOperation((left, right) => (left - right));
const multiply = binaryOperation((left, right) => (left * right));
const divide = binaryOperation((left, right) => (left / right));

const pi = () => Math.PI;
const e = () => Math.E;
