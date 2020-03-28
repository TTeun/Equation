package equation.ast;

import equation.parser.exception.EquationException;

public abstract class EquationAstNode {

    public String value;
    public final Type type;

    public EquationAstNode() {
        value = "Empty";
        type = Type.Error;
    }

    public EquationAstNode(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public abstract double evaluate() throws EquationException;

    public abstract void simplify() throws EquationException;


    public enum Type {
        Constant, Variable, BinaryOperation, UnaryOperation, PreDefinedUnaryFunction, CustomUnaryFunction, PreDefinedFunction, CustomFunction, Error
    }
}
