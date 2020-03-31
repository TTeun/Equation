package eqn.ast.predefined;

import eqn.ast.EqnAstNode;
import eqn.ast.EqnAstNodeBinaryOperation;
import eqn.ast.EqnAstNodeDouble;
import eqn.parser.exception.EqnException;

public class EqnAstNodeMultiply extends EqnAstNodeBinaryOperation {

    public EqnAstNodeMultiply(String value, EqnAstNode leftOperand, EqnAstNode rightOperand) {
        super(value, leftOperand, rightOperand,PrecedenceType.Multiplication);
    }

    @Override
    public double evaluate() throws EqnException {
        return leftOperand.evaluate() * rightOperand.evaluate();
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        leftOperand = leftOperand.simplify();
        rightOperand = rightOperand.simplify();

        if (leftOperand.type == Type.Constant) {
            if (Double.parseDouble(leftOperand.value) == 0.0) {
                return new EqnAstNodeDouble(0.0);
            } else if (Double.parseDouble(leftOperand.value) == 1.0) {
                return rightOperand;
            }
        }
        if (rightOperand.type == Type.Constant) {
            if (Double.parseDouble(rightOperand.value) == 0.0) {
                return new EqnAstNodeDouble(0.0);
            } else if (Double.parseDouble(rightOperand.value) == 1.0) {
                return leftOperand;
            }
        }
        return super.simplify();
    }

}
