package eqn.ast.predefined;

import eqn.ast.EqnAstNode;
import eqn.ast.EqnAstNodeBinaryOperation;
import eqn.parser.exception.EqnException;

public class EqnAstNodeAdd extends EqnAstNodeBinaryOperation {

    public EqnAstNodeAdd(String value, EqnAstNode leftOperand, EqnAstNode rightOperand) {
        super(value, leftOperand, rightOperand, PrecedenceType.Addition);
    }

    @Override
    public double evaluate() throws EqnException {
        return leftOperand.evaluate() + rightOperand.evaluate();
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        leftOperand = leftOperand.simplify();
        rightOperand = rightOperand.simplify();

        if (leftOperand.type == Type.Constant && Double.parseDouble(leftOperand.value) == 0.0) {
            return rightOperand;
        }
        if (rightOperand.type == Type.Constant && Double.parseDouble(rightOperand.value) == 0.0) {
            return leftOperand;
        }
        if (rightOperand.precedenceType != PrecedenceType.Addition && leftOperand.precedenceType == PrecedenceType.Addition) {
            return new EqnAstNodeAdd(value, rightOperand, leftOperand).simplify();
        }

        return super.simplify();
    }
}
