package equation.ast.predefined;

import equation.ast.EquationAstNode;
import equation.ast.EquationAstNodeBinaryOperation;
import equation.parser.exception.EquationException;

public class EquationAstNodeMultiply extends EquationAstNodeBinaryOperation {

    public EquationAstNodeMultiply(String value, EquationAstNode leftOperand, EquationAstNode rightOperand) {
        super(value, leftOperand, rightOperand);
    }

    @Override
    public double evaluate() throws EquationException {
        return leftOperand.evaluate() * rightOperand.evaluate();
    }
}
