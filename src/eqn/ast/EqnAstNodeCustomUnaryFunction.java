package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class EqnAstNodeCustomUnaryFunction extends EqnAstNode {

    protected EqnAstNode operand;

    public EqnAstNodeCustomUnaryFunction(String value, @NotNull EqnAstNode equationNode) {
        super(value, Type.CustomUnaryFunction);
        this.operand = equationNode;
    }

    @Override
    public String toString() {
        return value + '(' + operand.toString() + ')';
    }

    @Override
    public double evaluate() throws EqnException {
        throw new EqnException("Unary custom function evaluate not yet implemented (make this class abstract)!");
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        operand = operand.simplify();
        return this;
    }
}
