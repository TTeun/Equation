package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

abstract public class EqnAstNodeUnaryFunction extends EqnAstNode {

    public EqnAstNodeUnaryFunction(String value, Type type, @NotNull EqnAstNode equationNode, PrecedenceType precedenceType) throws EqnException {
        super(value, type, precedenceType);
        addOperand(equationNode);
    }

    EqnAstNode operand() {
        return getNodeAt(0);
    }
}
