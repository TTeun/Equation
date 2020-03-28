package eqn.ast.predefined;

import eqn.ast.EqnAstNode;
import eqn.ast.EqnAstNodeBinaryOperation;
import eqn.parser.exception.EqnException;

public class EqnAstNodeDivide extends EqnAstNodeBinaryOperation {

    public EqnAstNodeDivide(String value, EqnAstNode leftOperand, EqnAstNode rightOperand) {
        super(value, leftOperand, rightOperand);
    }

    @Override
    public double evaluate() throws EqnException {
        return leftOperand.evaluate() / rightOperand.evaluate();
    }
}
