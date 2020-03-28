package eqn.ast.predefined;

import eqn.ast.EqnAstNode;
import eqn.ast.EqnAstNodeBinaryOperation;
import eqn.ast.EqnAstNodeDouble;
import eqn.parser.exception.EqnException;

public class EqnAstNodePower extends EqnAstNodeBinaryOperation {

    public EqnAstNodePower(String value, EqnAstNode leftOperand, EqnAstNode rightOperand) {
        super(value, leftOperand, rightOperand);
    }

    @Override
    public double evaluate() throws EqnException {
        return Math.pow(leftOperand.evaluate(), rightOperand.evaluate());
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        leftOperand = leftOperand.simplify();
        rightOperand = rightOperand.simplify();

        if (rightOperand.type == Type.Constant) {
            if (Double.parseDouble(rightOperand.value) == 0.0D) {
                return new EqnAstNodeDouble(1.0D);
            }
            if (Double.parseDouble(rightOperand.value) == 1.0D) {
                return leftOperand;
            }
        }
        if (leftOperand.type == Type.Constant) {
            if (Double.parseDouble(leftOperand.value) == 1.0D) {
                return new EqnAstNodeDouble(1.0D);
            }
        }
        return super.simplify();
    }
}
