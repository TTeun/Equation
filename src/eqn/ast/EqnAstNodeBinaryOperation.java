package eqn.ast;

import eqn.parser.exception.EqnException;

public abstract class EqnAstNodeBinaryOperation extends EqnAstNode {

    public EqnAstNodeBinaryOperation(String value, EqnAstNode leftOperand, EqnAstNode rightOperand, PrecedenceType precedenceType) throws EqnException {
        super(value, Type.BinaryOperation, precedenceType);
        addOperand(leftOperand);
        addOperand(rightOperand);
    }

    EqnAstNode left() {
        return getNodeAt(0);
    }

    EqnAstNode right() {
        return getNodeAt(1);
    }

    @Override
    public String toString() {
        return (left().precedenceType.compareTo(this.precedenceType) >= 0 ? left().toString() : "(" + left().toString() + ")")
                + value
                + (right().precedenceType.compareTo(this.precedenceType) >= 0 ? right().toString() : "(" + right().toString() + ")");
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        simplifyChildren();

        if (left().type == Type.Constant && right().type == Type.Constant) {
            return new EqnAstNodeDouble(this.evaluate());
        }
        return this;
    }
}