package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class EqnAstNodeCos extends EqnAstNodePreDefinedUnaryFunction {

    public EqnAstNodeCos(@NotNull EqnAstNode equationNode) throws EqnException {
        super("cos", equationNode);
    }

    @Override
    public double evaluate() throws EqnException {
        return Math.cos(operand().evaluate());
    }
}
