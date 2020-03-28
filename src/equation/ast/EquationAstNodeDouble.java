package equation.ast;

public class EquationAstNodeDouble extends EquationAstNode {

    private final double valueOfNode;

    public EquationAstNodeDouble(String value) {
        super(value, Type.Constant);
        valueOfNode = Double.parseDouble(value);
    }

    @Override
    public String toString() {
        return Double.toString(valueOfNode);
    }
}
