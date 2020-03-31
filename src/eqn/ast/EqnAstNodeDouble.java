package eqn.ast;

public class EqnAstNodeDouble extends EqnAstNode {

    private double valueOfNode;

    public EqnAstNodeDouble(String value) {
        super(value, Type.Constant, PrecedenceType.Terminal);
        valueOfNode = Double.parseDouble(value);
    }

    public EqnAstNodeDouble(Double value) {
        super(Double.toString(value), Type.Constant, PrecedenceType.Terminal);
        valueOfNode = value;
    }

    @Override
    public String toString() {
        return Double.toString(valueOfNode);
    }

    @Override
    public double evaluate() {
        return valueOfNode;
    }

    @Override
    public EqnAstNode simplify() {
        return this;
    }
}


