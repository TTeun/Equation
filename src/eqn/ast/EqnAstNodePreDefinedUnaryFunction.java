package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public abstract class EqnAstNodePreDefinedUnaryFunction extends EqnAstNodeUnaryFunction {

    public EqnAstNodePreDefinedUnaryFunction(String value, @NotNull EqnAstNode equationNode) throws EqnException {
        super(value, EqnAstNode.Type.PreDefinedUnaryFunction, equationNode, PrecedenceType.Function);
    }

    public static EqnAstNodePreDefinedUnaryFunction create(String value, @NotNull EqnAstNode equationNode) throws EqnException {
        if (value.equals("sin")) {
            return new EqnAstNodeSin(equationNode);
        }
        if (value.equals("cos")) {
            return new EqnAstNodeCos(equationNode);
        }
        if (value.equals("tan")) {
            return new EqnAstNodeTan( equationNode);
        }
        if (value.equals("exp")) {
            return new EqnAstNodeExp( equationNode);
        }
        throw new EqnException("PreDefinedUnaryFunction " + value + " not yet implemented!");
    }

    @Override
    public String toString() {
        return value + '(' + operand().toString() + ')';
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        simplifyChildren();
        if (operand().type == Type.Constant) {
            return new EqnAstNodeDouble(this.evaluate());
        }
        return this;
    }
}
