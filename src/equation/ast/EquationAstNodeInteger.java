package equation.ast;

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
}
