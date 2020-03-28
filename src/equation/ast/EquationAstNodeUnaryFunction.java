package equation.ast;

import org.jetbrains.annotations.NotNull;

public class EquationAstNodeUnaryFunction extends EquationAstNode {

    EquationAstNode operand;

    public EquationAstNodeUnaryFunction(String value, @NotNull EquationAstNode equationNode) {
        super(value, Type.CustomUnaryFunction);
        this.operand = equationNode;
    }

    @Override
    public String toString() {
        return value + '(' + operand.toString() + ')';
    }
}
