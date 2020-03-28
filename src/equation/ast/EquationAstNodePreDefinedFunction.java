package equation.ast;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class EquationAstNodePreDefinedFunction extends EquationAstNode {

    public int arity;
    Vector<EquationAstNode> operands;

    public EquationAstNodePreDefinedFunction(String value) {
        super(value, Type.PreDefinedFunction);
        operands = new Vector<EquationAstNode>(0);
    }


    public EquationAstNodePreDefinedFunction(String value, int arity) {
        super(value, EquationAstNode.Type.CustomFunction);
        operands = new Vector<EquationAstNode>(0);
        this.arity = arity;
    }

    public void addOperand(@NotNull EquationAstNode operand) {
        this.operands.add(operand);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(value + '(');
        for (int i = 0; i < operands.size() - 1; ++i) {
            stringBuffer.append(operands.elementAt(i).toString() + ", ");
        }
        stringBuffer.append(operands.lastElement().toString());

        stringBuffer.append(")");
        return new String(stringBuffer);
    }
}