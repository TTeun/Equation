package eqn.ast.predefined;

import eqn.ast.EqnAstNode;
import eqn.ast.EqnAstNodeBinaryOperation;
import eqn.ast.EqnAstNodeUnaryOperation;
import eqn.parser.exception.EqnException;

public class EqnAstNodeSubtract extends EqnAstNodeBinaryOperation {

    public EqnAstNodeSubtract(String value, EqnAstNode leftOperand, EqnAstNode rightOperand) {
        super(value, leftOperand, rightOperand, PrecedenceType.Addition);
    }

    @Override
    public double evaluate() throws EqnException {
        return leftOperand.evaluate() - rightOperand.evaluate();
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        leftOperand = leftOperand.simplify();
        rightOperand = rightOperand.simplify();

        return new EqnAstNodeAdd("+", leftOperand, new EqnAstNodeUnaryOperation("-", rightOperand)).simplify();
//
//        if (leftOperand.type == Type.Constant && Double.parseDouble(leftOperand.value) == 0.0) {
//            return (new EqnAstNodeUnaryOperation("-", rightOperand).simplify());
//        }
//        if (rightOperand.type == Type.Constant && Double.parseDouble(rightOperand.value) == 0.0) {
//            return leftOperand;
//        }
//        return super.simplify();
    }

}
