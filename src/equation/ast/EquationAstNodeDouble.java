package equation.ast;

import equation.parser.exception.EquationException;

public class EquationAstNodeDouble extends EquationAstNode {

    private final double valueOfNode;

    public EquationAstNodeDouble(String value) {
        super(value, Type.Constant);
        valueOfNode = Double.parseDouble(value);
    }

    public EquationAstNodeDouble(Double value) {
        super(Double.toString(value), Type.Constant);
        valueOfNode = value;
    }

    @Override
    public String toString() {
        return Double.toString(valueOfNode);
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


