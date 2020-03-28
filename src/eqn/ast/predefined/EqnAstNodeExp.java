package eqn.ast.predefined;

import eqn.ast.EqnAstNode;
import eqn.ast.EqnAstNodePreDefinedUnaryFunction;
import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class EqnAstNodeExp extends EqnAstNodePreDefinedUnaryFunction {

    public EqnAstNodeExp(String value, @NotNull EqnAstNode equationNode) {
        super(value, equationNode);
    }

    @Override
    public double evaluate() throws EqnException {
        return Math.exp(operand.evaluate());
    }

}
