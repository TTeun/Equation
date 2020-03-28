package eqn;

import eqn.ast.EqnAst;
import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

public class Eqn {

    final private EqnHead equationHead;
    final private EqnAst equationAst;

    public Eqn(@NotNull EqnHead equationHead, @NotNull EqnAst equationAst) throws EqnException {
        this.equationHead = equationHead;
        this.equationAst = equationAst;
        this.equationAst.simplify();
        System.out.println("Equation created:");
        System.out.println("\t" + equationHead.toString());
        System.out.println("\t" + equationAst.toString());
    }

    public Double evaluate() throws EqnException {
        return equationAst.evaluate();
    }

}
