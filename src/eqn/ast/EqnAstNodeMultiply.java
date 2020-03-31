package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodeMultiply extends EqnAstNodeBinaryOperation {

    public EqnAstNodeMultiply(EqnAstNode left, EqnAstNode right) throws EqnException {
        super("*", left, right, PrecedenceType.Multiplication);
    }

    @Override
    public double evaluate() throws EqnException {
        return left().evaluate() * right().evaluate();
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        simplifyChildren();

        if (left().type == Type.Constant) {
            if (Double.parseDouble(left().value) == 0.0) {
                return new EqnAstNodeDouble(0.0);
            } else if (Double.parseDouble(left().value) == 1.0) {
                return right();
            }
        }
        if (right().type == Type.Constant) {
            if (Double.parseDouble(right().value) == 0.0) {
                return new EqnAstNodeDouble(0.0);
            } else if (Double.parseDouble(right().value) == 1.0) {
                return left();
            }
        }
        return super.simplify();
    }

}
