package eqn.ast;

import eqn.parser.exception.EqnException;

public abstract class EqnAstNode {

    public final Type type;
    public final String value;
    public PrecedenceType precedenceType = PrecedenceType.Function;

    public EqnAstNode() {
        value = "Empty";
        type = Type.Error;
    }

    public EqnAstNode(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public abstract double evaluate() throws EqnException;

    public abstract EqnAstNode simplify() throws EqnException;

    public enum Type {
        Constant, Variable, BinaryOperation, UnaryOperation, PreDefinedUnaryFunction, CustomUnaryFunction, PreDefinedFunction, CustomFunction, Error
    }

    public enum PrecedenceType {
        Addition, Multiplication, Power, Function
    }
}
