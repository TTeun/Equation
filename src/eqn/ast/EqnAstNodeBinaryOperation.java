package eqn.ast;

import eqn.parser.exception.EqnException;

public abstract class EqnAstNodeBinaryOperation extends EqnAstNode {

    protected EqnAstNode leftOperand;
    protected EqnAstNode rightOperand;

    public EqnAstNodeBinaryOperation(String value, EqnAstNode leftOperand, EqnAstNode rightOperand, PrecedenceType precedenceType) {
        super(value, Type.BinaryOperation);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.precedenceType = precedenceType;
    }

    @Override
    public String toString() {
        return (leftOperand.precedenceType.compareTo(this.precedenceType) >= 0 ? leftOperand.toString() : "(" + leftOperand.toString() + ")")
                + value
                + (rightOperand.precedenceType.compareTo(this.precedenceType) >= 0 ? rightOperand.toString() : "(" + rightOperand.toString() + ")");
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        leftOperand = leftOperand.simplify();

        if (leftOperand.type == Type.Constant && rightOperand.type == Type.Constant) {
            return new EqnAstNodeDouble(this.evaluate());
        }
        return this;
    }

}