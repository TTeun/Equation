package equation.ast;

public class EquationAstNodeUnaryOperation extends EquationAstNode {

    public EquationAstNode operand;

    public EquationAstNodeUnaryOperation(String value, EquationAstNode operand) {
        super(value, Type.UnaryOperation);
        this.operand = operand;
    }

    @Override
    public String toString() {
        return "(" + value + " " + operand.toString() + ")";
    }
}
