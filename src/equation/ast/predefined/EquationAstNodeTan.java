package equation.ast.predefined;

import equation.ast.EquationAstNode;
import equation.ast.EquationAstNodePreDefinedUnaryFunction;
import equation.parser.exception.EquationException;
import org.jetbrains.annotations.NotNull;

public class EquationAstNodeTan extends EquationAstNodePreDefinedUnaryFunction {

    public EquationAstNodeTan(String value, @NotNull EquationAstNode equationNode) {
        super(value, equationNode);
    }

    @Override
    public double evaluate() throws EquationException {
        return Math.tan(operand.evaluate());
    }
}
