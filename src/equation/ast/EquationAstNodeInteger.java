package equation.ast;

import equation.parser.exception.EquationException;

public class EquationAstNodeInteger extends EquationAstNode {

    private final int valueOfNode;

    public EquationAstNodeInteger(String value) {
        super(value, Type.Constant);
        valueOfNode = Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return Integer.toString(valueOfNode);
    }

    @Override
    public double evaluate() throws EquationException {
        return valueOfNode;
    }

    @Override
    public void simplify() throws EquationException {
        return;
    }
}
