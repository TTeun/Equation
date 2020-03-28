package equation.ast.predefined;

import equation.ast.EquationAstNode;
import equation.ast.EquationAstNodePreDefinedUnaryFunction;
import equation.parser.exception.EquationException;
import org.jetbrains.annotations.NotNull;

public class EquationAstNodeSin extends EquationAstNodePreDefinedUnaryFunction {

    public EquationAstNodeSin(String value, @NotNull EquationAstNode equationNode) {
        super(value, equationNode);
    }

    @Override
    public double evaluate() throws EquationException {
        return Math.sin(operand.evaluate());
    }
}
