package equation.ast;

import equation.parser.exception.EquationException;

public class EquationAstNodeVariable extends EquationAstNode {

    public EquationAstNodeVariable(String value) {
        super(value, Type.Variable);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public double evaluate() throws EquationException {
        throw new EquationException("Evaluate for variable not yet implemented!");
    }

    @Override
    public void simplify() throws EquationException {
        return;
    }
}
