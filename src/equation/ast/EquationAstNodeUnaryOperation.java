package equation.ast;

import equation.parser.exception.BadBodyException;
import equation.parser.exception.EquationException;

public class EquationAstNodeUnaryOperation extends EquationAstNode {

    public EquationAstNode operand;

    public EquationAstNodeUnaryOperation(String value, EquationAstNode operand) throws BadBodyException {
        super(value, Type.UnaryOperation);
        if (value != "-") {
            throw new BadBodyException("Only the unary '-' is implemented");
        }
        this.operand = operand;
    }

    @Override
    public String toString() {
        return "(" + value + " " + operand.toString() + ")";
    }

    @Override
    public double evaluate() throws EquationException {
        return -1.D * operand.evaluate();
    }

    @Override
    public void simplify() throws EquationException {
        operand.simplify();
        try {
            double val = operand.evaluate();
            operand = new EquationAstNodeDouble(val);
        } catch (EquationException e) {

        }
    }
}
