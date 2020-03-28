package equation.ast;

import org.jetbrains.annotations.NotNull;

public class EquationAstNodePreDefinedUnaryFunction extends EquationAstNode {

    EquationAstNode operand;

    public EquationAstNodePreDefinedUnaryFunction(String value, @NotNull EquationAstNode equationNode) {
        super(value, EquationAstNode.Type.PreDefinedUnaryFunction);
        this.operand = equationNode;
    }

    @Override
    public String toString() {
        return value + '(' + operand.toString() + ')';
    }

}
