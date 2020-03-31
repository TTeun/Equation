package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodeDivide extends EqnAstNodeBinaryOperation {

    public EqnAstNodeDivide(EqnAstNode leftOperand, EqnAstNode rightOperand) throws EqnException {
        super("/", leftOperand, rightOperand, PrecedenceType.Multiplication);
    }

    @Override
    public double evaluate() throws EqnException {
        return left().evaluate() / right().evaluate();
    }
}
