package equation.ast;

public class EquationAstNode {

    public final String value;
    public final Type type;

    public EquationAstNode() {
        value = "Empty";
        type = Type.Error;
    }

    public EquationAstNode(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public enum Type {
        Constant, Variable, BinaryOperation, UnaryOperation, PreDefinedUnaryFunction, CustomUnaryFunction, PreDefinedFunction, CustomFunction, Error
    }
}
