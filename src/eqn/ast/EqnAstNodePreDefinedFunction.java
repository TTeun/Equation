package eqn.ast;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public abstract class EqnAstNodePreDefinedFunction extends EqnAstNode {

    public int arity;
    Vector<EqnAstNode> operands;

    public EqnAstNodePreDefinedFunction(String value) {
        super(value, Type.PreDefinedFunction);
        operands = new Vector<EqnAstNode>(0);
    }


    public EqnAstNodePreDefinedFunction(String value, int arity) {
        super(value, EqnAstNode.Type.CustomFunction);
        operands = new Vector<EqnAstNode>(0);
        this.arity = arity;
    }

    public void addOperand(@NotNull EqnAstNode operand) {
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
