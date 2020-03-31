package eqn.ast;

import eqn.parser.exception.EqnException;

import java.util.Vector;

public class EqnAstNodeSetMultiply extends EqnAstNode {

    double constantOperand = 1.0D;

    public EqnAstNodeSetMultiply() {
        super("Add", Type.Multiplication, PrecedenceType.Multiplication);
    }

    public EqnAstNodeSetMultiply(EqnAstNode left, EqnAstNode right) throws EqnException {
        super("Add", Type.Multiplication, PrecedenceType.Multiplication);
        addOperand(left);
        addOperand(right);
    }

    protected double getConstant() {
        return constantOperand;
    }

    public void addOperand(EqnAstNode eqnAstNode) throws EqnException {
        if (eqnAstNode.type == Type.Constant) {
            constantOperand *= eqnAstNode.evaluate();
        } else if (eqnAstNode.type == Type.Multiplication) {
            constantOperand *= eqnAstNode.getConstant();
            for (int i = 0; i != eqnAstNode.operands.size(); ++i) {
                addOperand(eqnAstNode.operands.elementAt(i));
            }
        } else {
            operands.add(eqnAstNode);
        }
    }

    @Override
    public double evaluate() throws EqnException {
        double result = constantOperand;
        for (int i = 0; i != operands.size(); ++i) {
            result *= operands.elementAt(i).evaluate();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(constantOperand == 1.0D ? "" : constantOperand + "*");
        if (operands.size() != 0) {
            for (int i = 0; i < operands.size(); ++i) {
                stringBuffer.append(operands.elementAt(i).toString() + "*");
            }
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        return new String(stringBuffer);
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        for (int i = 0; i != operands.size(); ++i) {
            operands.set(i, operands.elementAt(i).simplify());
        }
        Vector<EqnAstNode> updatedOperands = new Vector<>();
        for (int i = 0; i != operands.size(); ++i) {
            if (operands.elementAt(i).type == Type.Constant) {
                constantOperand *= operands.elementAt(i).evaluate();
            } else {
                updatedOperands.add(operands.elementAt(i));
            }
        }
        operands = updatedOperands;
        if (operands.size() == 0) {
            return new EqnAstNodeDouble(constantOperand);
        }
        return this;
    }
}