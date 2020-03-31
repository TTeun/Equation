package eqn.ast;

public class EqnAstNodeDouble extends EqnAstNode {

    private final double valueOfNode;

    public EqnAstNodeDouble(String value) {
        super(value, Type.Constant);
        valueOfNode = Double.parseDouble(value);
    }

    public EqnAstNodeDouble(Double value) {
        super(Double.toString(value), Type.Constant);
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


