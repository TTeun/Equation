package equation;

import equation.ast.EquationAstNode;
import org.jetbrains.annotations.NotNull;

public class Equation {

    final private EquationHead equationHead;
    final private EquationAstNode equationAstNode;

    public Equation(@NotNull EquationHead equationHead, @NotNull EquationAstNode equationAstNode) {
        this.equationHead = equationHead;
        this.equationAstNode = equationAstNode;
        System.out.println("Equation created:");
        System.out.println("\t" + equationHead.toString());
        System.out.println("\t" + equationAstNode.toString());
    }
}
