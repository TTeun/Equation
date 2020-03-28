package equation.ast;

import equation.ast.predefined.EquationAstNodeCos;
import equation.ast.predefined.EquationAstNodeExp;
import equation.ast.predefined.EquationAstNodeSin;
import equation.ast.predefined.EquationAstNodeTan;
import equation.parser.exception.EquationException;
import org.jetbrains.annotations.NotNull;

public abstract class EquationAstNodePreDefinedUnaryFunction extends EquationAstNode {

    protected EquationAstNode operand;

    public EquationAstNodePreDefinedUnaryFunction(String value, @NotNull EquationAstNode equationNode) {
        super(value, EquationAstNode.Type.PreDefinedUnaryFunction);
        this.operand = equationNode;
    }

    public static EquationAstNodePreDefinedUnaryFunction create(String value, @NotNull EquationAstNode equationNode) throws EquationException {
        if (value.equals("sin")) {
            return new EquationAstNodeSin(value, equationNode);
        }
        if (value.equals("cos")) {
            return new EquationAstNodeCos(value, equationNode);
        }
        if (value.equals("tan")) {
            return new EquationAstNodeTan(value, equationNode);
        }
        if (value.equals("exp")) {
            return new EquationAstNodeExp(value, equationNode);
        }
        throw new EquationException("PreDefinedUnaryFunction " + value + " not yet implemented!");
    }

    @Override
    public String toString() {
        return value + '(' + operand.toString() + ')';
    }

    @Override
    public void simplify() throws EquationException {
        operand.simplify();
    }
}
