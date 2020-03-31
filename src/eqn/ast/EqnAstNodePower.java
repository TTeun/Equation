package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodePower extends EqnAstNodeBinaryOperation {

    public EqnAstNodePower(EqnAstNode leftOperand, EqnAstNode rightOperand) throws EqnException {
        super("^", leftOperand, rightOperand, PrecedenceType.Power);
    }

    @Override
    public double evaluate() throws EqnException {
        return Math.pow(left().evaluate(), right().evaluate());
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        simplifyChildren();

        if (right().type == Type.Constant) {
            if (Double.parseDouble(right().value) == 0.0D) {
                return new EqnAstNodeDouble(1.0D);
            }
            if (Double.parseDouble(right().value) == 1.0D) {
                return left();
            }
        }
        if (left().type == Type.Constant) {
            if (Double.parseDouble(left().value) == 1.0D) {
                return new EqnAstNodeDouble(1.0D);
            }
        }
        return super.simplify();
    }
}
