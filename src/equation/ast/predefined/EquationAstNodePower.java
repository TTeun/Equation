package equation.ast.predefined;

import equation.ast.EquationAstNode;
import equation.ast.EquationAstNodeBinaryOperation;
import equation.parser.exception.EquationException;

public class EquationAstNodePower extends EquationAstNodeBinaryOperation {

    public EquationAstNodePower(String value, EquationAstNode leftOperand, EquationAstNode rightOperand) {
        super(value, leftOperand, rightOperand);
    }

    @Override
    public double evaluate() throws EquationException {
        return Math.pow(leftOperand.evaluate(), rightOperand.evaluate());
    }


}
