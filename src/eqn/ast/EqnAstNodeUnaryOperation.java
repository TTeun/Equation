package eqn.ast;

import eqn.parser.exception.BadBodyException;
import eqn.parser.exception.EqnException;

public class EqnAstNodeUnaryOperation extends EqnAstNode {

    public EqnAstNode operand;

    public EqnAstNodeUnaryOperation(String value, EqnAstNode operand) throws BadBodyException {
        super(value, Type.UnaryOperation);
        if (value != "-") {
            throw new BadBodyException("Only the unary '-' is implemented");
        }
        this.operand = operand;
    }

    @Override
    public String toString() {
        return value + " " + operand.toString();
    }

    @Override
    public double evaluate() throws EqnException {
        return -1.D * operand.evaluate();
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        operand = operand.simplify();
        if (operand.type == Type.Constant) {
            return new EqnAstNodeDouble(this.evaluate());
        }
        return this;
    }
}
