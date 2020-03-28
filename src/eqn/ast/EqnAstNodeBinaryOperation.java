package eqn.ast;

import eqn.parser.exception.EqnException;

public abstract class EqnAstNodeBinaryOperation extends EqnAstNode {

    protected EqnAstNode leftOperand;
    protected EqnAstNode rightOperand;

    public EqnAstNodeBinaryOperation(String value, EqnAstNode leftOperand, EqnAstNode rightOperand) {
        super(value, Type.BinaryOperation);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public String toString() {
        return '(' + leftOperand.toString() + value + rightOperand.toString() + ")";
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        leftOperand = leftOperand.simplify();
        rightOperand = rightOperand.simplify();

        try {
            double lhs = leftOperand.evaluate();
            leftOperand = new EqnAstNodeDouble(lhs);
        } catch (EqnException e) {
            return this;
        }
        try {
            double rhs = rightOperand.evaluate();
            rightOperand = new EqnAstNodeDouble(rhs);
        } catch (EqnException e) {
            return this;
        }
        return new EqnAstNodeDouble(this.evaluate());
    }
}