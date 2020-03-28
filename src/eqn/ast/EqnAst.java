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
        try {
            double val = root.evaluate();
            root = new EqnAstNodeDouble(val);
        } catch (EqnException e) {

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
