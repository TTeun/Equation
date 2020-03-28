package eqn.ast.predefined;

import eqn.ast.EqnAstNode;
import eqn.ast.EqnAstNodePreDefinedUnaryFunction;
import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class EqnAstNodeTan extends EqnAstNodePreDefinedUnaryFunction {

    public EqnAstNodeTan(String value, @NotNull EqnAstNode equationNode) {
        super(value, equationNode);
    }

    @Override
    public double evaluate() throws EqnException {
        return Math.tan(operand.evaluate());
    }
}
