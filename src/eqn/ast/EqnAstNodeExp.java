package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class EqnAstNodeExp extends EqnAstNodePreDefinedUnaryFunction {

    public EqnAstNodeExp(@NotNull EqnAstNode equationNode) throws EqnException {
        super("exp", equationNode);
    }

    @Override
    public double evaluate() throws EqnException {
        return Math.exp(operand().evaluate());
    }

}
