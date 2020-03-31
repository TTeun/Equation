package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodeUnaryMinus extends EqnAstNodeUnaryFunction {

    public EqnAstNodeUnaryMinus(EqnAstNode operand) throws EqnException {
        super("-", Type.UnaryOperation, operand, PrecedenceType.UnaryMinus);
    }

    @Override
    public String toString() {
        return "-" + operand().toString();
    }

    @Override
    public double evaluate() throws EqnException {
        return -1.D * operand().evaluate();
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        simplifyChildren();
        if (operand().type == Type.Constant) {
            return new EqnAstNodeDouble(this.evaluate());
        }
        return this;
    }
}
