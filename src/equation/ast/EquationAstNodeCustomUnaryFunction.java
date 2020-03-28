package equation.ast;

import equation.parser.exception.EquationException;
import org.jetbrains.annotations.NotNull;

public class EquationAstNodeCustomUnaryFunction extends EquationAstNode {

    protected EquationAstNode operand;

    public EquationAstNodeCustomUnaryFunction(String value, @NotNull EquationAstNode equationNode) {
        super(value, Type.CustomUnaryFunction);
        this.operand = equationNode;
    }

    @Override
    public String toString() {
        return value + '(' + operand.toString() + ')';
    }

    @Override
    public double evaluate() throws EquationException {
        throw new EquationException("Unary custom function evaluate not yet implemented (make this class abstract)!");
    }

    @Override
    public void simplify() throws EquationException {
        throw new EquationException("Unary custom function simplify not yet implemented (make this class abstract)!");
    }
}
