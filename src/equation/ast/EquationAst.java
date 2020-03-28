package equation.ast;

import equation.parser.exception.EquationException;
import org.jetbrains.annotations.NotNull;

public class EquationAst {
    public EquationAstNode root;

    public EquationAst(@NotNull EquationAstNode root) {
        this.root = root;
    }

    public void simplify() throws EquationException {
        root.simplify();
        try {
            double val = root.evaluate();
            root = new EquationAstNodeDouble(val);
        } catch (EquationException e) {

        }
    }

    @Override
    public String toString() {
        return "EquationAst{" +
                "root=" + root +
                '}';
    }

    public double evaluate() throws EquationException {
        return root.evaluate();
    }
}
