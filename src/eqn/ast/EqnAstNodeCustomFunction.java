package eqn.ast;

import eqn.parser.exception.EqnException;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class EqnAstNodeCustomFunction extends EqnAstNode {

    public int arity;
    Vector<EqnAstNode> operands;

    public EqnAstNodeCustomFunction(String value) {
        super(value, Type.CustomFunction);
        operands = new Vector<>(0);
    }


    public EqnAstNodeCustomFunction(String value, int arity) {
        super(value, Type.CustomFunction);
        operands = new Vector<>(0);
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

    @Override
    public double evaluate() throws EqnException {
        throw new EqnException("Custom function evaluate not yet implemented (make this class abstract)!");
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        for (int i = 0; i != arity; ++i) {
            operands.set(i, operands.elementAt(i).simplify());
        }
        return this;
    }
}
