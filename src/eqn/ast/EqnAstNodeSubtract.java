package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodeSubtract extends EqnAstNodeBinaryOperation {

    public EqnAstNodeSubtract(EqnAstNode leftOperand, EqnAstNode rightOperand) throws EqnException {
        super("-", leftOperand, rightOperand, PrecedenceType.Addition);
    }

    @Override
    public double evaluate() throws EqnException {
        return left().evaluate() - right().evaluate();
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        simplifyChildren();

        return new EqnAstNodeSetAdd(left(), new EqnAstNodeUnaryMinus(right())).simplify();
    }

}
