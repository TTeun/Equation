package equation;

import equation.ast.EquationAst;
import equation.parser.exception.EquationException;
import org.jetbrains.annotations.NotNull;

public class Equation {

    final private EquationHead equationHead;
    final private EquationAst equationAst;

    public Equation(@NotNull EquationHead equationHead, @NotNull EquationAst equationAst) throws EquationException {
        this.equationHead = equationHead;
        this.equationAst = equationAst;
        this.equationAst.simplify();
        System.out.println("Equation created:");
        System.out.println("\t" + equationHead.toString());
        System.out.println("\t" + equationAst.toString());
    }

    public Double evaluate() throws EquationException {
        return equationAst.evaluate();
    }

}
