package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodeCustomFunction extends EqnAstNodeFunction {

    public EqnAstNodeCustomFunction(String value) {
        super(value, Type.CustomFunction, PrecedenceType.Function);
    }

    @Override
    public double evaluate() throws EqnException {
        throw new EqnException("Custom function evaluate not yet implemented (make this class abstract)!");
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        for (int i = 0; i != arity(); ++i) {
            operands.set(i, operands.elementAt(i).simplify());
        }
        return this;
    }
}
