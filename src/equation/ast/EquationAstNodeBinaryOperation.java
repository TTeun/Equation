package equation.ast;

import equation.parser.exception.EquationException;

public abstract class EquationAstNodeBinaryOperation extends EquationAstNode {

    protected EquationAstNode leftOperand;
    protected EquationAstNode rightOperand;

    public EquationAstNodeBinaryOperation(String value, EquationAstNode leftOperand, EquationAstNode rightOperand) {
        super(value, Type.BinaryOperation);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public String toString() {
        return '(' + leftOperand.toString() + value + rightOperand.toString() + ")";
    }

    @Override
    public void simplify() throws EquationException {
        leftOperand.simplify();
        rightOperand.simplify();

        try {
            double lhs = leftOperand.evaluate();
            leftOperand = new EquationAstNodeDouble(lhs);
        } catch (EquationException e) {

        }
        try {
            double rhs = rightOperand.evaluate();
            rightOperand = new EquationAstNodeDouble(rhs);
        } catch (EquationException e) {
        }
    }
}