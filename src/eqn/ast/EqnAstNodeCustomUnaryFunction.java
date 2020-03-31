package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class EqnAstNodeCustomUnaryFunction extends EqnAstNodeUnaryFunction {

    public EqnAstNodeCustomUnaryFunction(String value, @NotNull EqnAstNode equationNode) throws EqnException {
        super(value, Type.CustomUnaryFunction, equationNode, PrecedenceType.Function);
    }

    @Override
    public String toString() {
        return value + '(' + operand().toString() + ')';
    }

    @Override
    public double evaluate() throws EqnException {
        throw new EqnException("Unary custom function evaluate not yet implemented (make this class abstract)!");
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        simplifyChildren();
        return this;
    }
}
