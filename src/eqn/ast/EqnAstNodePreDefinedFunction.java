package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public abstract class EqnAstNodePreDefinedFunction extends EqnAstNode {


    public EqnAstNodePreDefinedFunction(String value) {
        super(value, Type.PreDefinedFunction, PrecedenceType.Function);
        operands = new Vector<>(0);
    }

    protected int arity() {
        return operands.size();
    }

    public void addOperand(@NotNull EqnAstNode operand) throws EqnException {
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
