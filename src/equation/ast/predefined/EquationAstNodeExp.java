package equation.ast.predefined;

import equation.ast.EquationAstNode;
import equation.ast.EquationAstNodePreDefinedUnaryFunction;
import equation.parser.exception.EquationException;
import org.jetbrains.annotations.NotNull;

public class EquationAstNodeExp extends EquationAstNodePreDefinedUnaryFunction {

    public EquationAstNodeExp(String value, @NotNull EquationAstNode equationNode) {
        super(value, equationNode);
    }

    @Override
    public double evaluate() throws EquationException {
        return Math.exp(operand.evaluate());
    }
}
