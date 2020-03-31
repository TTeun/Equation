package eqn.ast;

import eqn.ast.predefined.EqnAstNodeCos;
import eqn.ast.predefined.EqnAstNodeExp;
import eqn.ast.predefined.EqnAstNodeSin;
import eqn.ast.predefined.EqnAstNodeTan;
import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public abstract class EqnAstNodePreDefinedUnaryFunction extends EqnAstNode {

    protected EqnAstNode operand;

    public EqnAstNodePreDefinedUnaryFunction(String value, @NotNull EqnAstNode equationNode) {
        super(value, EqnAstNode.Type.PreDefinedUnaryFunction);
        this.operand = equationNode;
    }

    public static EqnAstNodePreDefinedUnaryFunction create(String value, @NotNull EqnAstNode equationNode) throws EqnException {
        if (value.equals("sin")) {
            return new EqnAstNodeSin(value, equationNode);
        }
        if (value.equals("cos")) {
            return new EqnAstNodeCos(value, equationNode);
        }
        if (value.equals("tan")) {
            return new EqnAstNodeTan(value, equationNode);
        }
        if (value.equals("exp")) {
            return new EqnAstNodeExp(value, equationNode);
        }
        throw new EqnException("PreDefinedUnaryFunction " + value + " not yet implemented!");
    }

    @Override
    public String toString() {
        return value + '(' + operand.toString() + ')';
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        operand = operand.simplify();
        if (operand.type == Type.Constant) {
            return new EqnAstNodeDouble(this.evaluate());
        }
        return this;
    }
}
