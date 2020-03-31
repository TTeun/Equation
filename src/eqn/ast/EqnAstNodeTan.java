package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class EqnAstNodeTan extends EqnAstNodePreDefinedUnaryFunction {

    public EqnAstNodeTan(@NotNull EqnAstNode equationNode) throws EqnException {
        super("tan", equationNode);
    }

    @Override
    public double evaluate() throws EqnException {
        return Math.tan(operand().evaluate());
    }
}
