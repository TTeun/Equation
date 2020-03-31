package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodeSetAdd extends EqnAstNodePreDefinedFunction {
    public EqnAstNodeSetAdd() {
        super("Add");
    }

    @Override
    public double evaluate() throws EqnException {
        double result = 0.0D;
        for (int i = 0; i != operands.size(); ++i) {
            result += operands.elementAt(i).evaluate();
        }
        return result;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        return null;
    }
}
