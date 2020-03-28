package equation.ast;

public class EquationAstNodeVariable extends EquationAstNode {

    public EquationAstNodeVariable(String value) {
        super(value, Type.Variable);
    }

    @Override
    public String toString() {
        return value;
    }
}
