package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodeVariable extends EqnAstNode {

    public EqnAstNodeVariable(String value) {
        super(value, Type.Variable);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public double evaluate() throws EqnException {
        throw new EqnException("Evaluate for variable not yet implemented!");
    }

    @Override
    public EqnAstNode simplify() {
        return this;
    }
}
