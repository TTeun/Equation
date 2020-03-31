package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class EqnAst {
    public EqnAstNode root;

    public EqnAst(@NotNull EqnAstNode root) {
        this.root = root;
    }

    public void simplify() throws EqnException {
        root = root.simplify();
        if (root.type == EqnAstNode.Type.Constant) {
            root = new EqnAstNodeDouble(Double.parseDouble(root.value));
        }
    }

    @Override
    public String toString() {
        return "EquationAst{" +
                "root=" + root +
                '}';
    }

    public double evaluate() throws EqnException {
        return root.evaluate();
    }
}
