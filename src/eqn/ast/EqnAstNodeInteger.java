package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodeInteger extends EqnAstNode {

    private final int valueOfNode;

    public EqnAstNodeInteger(String value) {
        super(value, Type.Constant);
        valueOfNode = Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return Integer.toString(valueOfNode);
    }

    @Override
    public double evaluate() throws EqnException {
        return valueOfNode;
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        return this;
    }
}
