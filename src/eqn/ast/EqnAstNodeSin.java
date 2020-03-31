package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class EqnAstNodeSin extends EqnAstNodePreDefinedUnaryFunction {

    public EqnAstNodeSin(@NotNull EqnAstNode equationNode) throws EqnException {
        super("sin", equationNode);
    }

    @Override
    public double evaluate() throws EqnException {
        return Math.sin(operand().evaluate());
    }
}
