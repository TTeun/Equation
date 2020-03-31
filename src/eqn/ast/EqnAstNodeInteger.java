package eqn.ast;

public class EqnAstNodeInteger extends EqnAstNode {

    private final int valueOfNode;

    public EqnAstNodeInteger(String value) {
        super(value, Type.Constant);
        valueOfNode = Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return Integer.toString(valueOfNode);
    }

    @Override
    public double evaluate()  {
        return valueOfNode;
    }

    @Override
    public EqnAstNode simplify()  {
        return this;
    }
}