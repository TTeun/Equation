package equation.ast;

public class EquationAstNodeBinaryOperation extends EquationAstNode {

    final private EquationAstNode leftOperand;
    final private EquationAstNode rightOperand;

    public EquationAstNodeBinaryOperation(String value, EquationAstNode leftOperand, EquationAstNode rightOperand) {
        super(value, Type.BinaryOperation);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public String toString() {
        return '(' + leftOperand.toString() + value + rightOperand.toString() + ")";
    }

}