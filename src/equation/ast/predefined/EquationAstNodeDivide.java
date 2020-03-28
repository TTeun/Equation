package equation.ast.predefined;

import equation.ast.EquationAstNode;
import equation.ast.EquationAstNodeBinaryOperation;
import equation.parser.exception.EquationException;

public class EquationAstNodeDivide extends EquationAstNodeBinaryOperation {

    public EquationAstNodeDivide(String value, EquationAstNode leftOperand, EquationAstNode rightOperand) {
        super(value, leftOperand, rightOperand);
    }

    @Override
    public double evaluate() throws EquationException {
        return leftOperand.evaluate() / rightOperand.evaluate();
    }


}
