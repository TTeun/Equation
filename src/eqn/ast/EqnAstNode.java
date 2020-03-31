package eqn.ast;

import eqn.parser.exception.EqnException;

import java.util.Vector;

public abstract class EqnAstNode {

    protected final Type type;
    protected final String value;
    protected final PrecedenceType precedenceType;
    protected Vector<EqnAstNode> operands;


    public EqnAstNode(PrecedenceType precedenceType) {
        this.precedenceType = precedenceType;
        value = "Empty";
        type = Type.Error;
        operands = new Vector<>();
    }

    public EqnAstNode(String value, Type type, PrecedenceType precedenceType) {
        this.value = value;
        this.type = type;
        this.precedenceType = precedenceType;
        operands = new Vector<>();
    }

    protected double getConstant() {
        throw new UnsupportedOperationException();
    }

    EqnAstNode getNodeAt(int index) {
        if (index >= operands.size()) {
            throw new ArrayIndexOutOfBoundsException("Node index too high!");
        }
        return operands.elementAt(index);
    }

    protected void setNodeAt(int index, EqnAstNode eqnAstNode) {
        if (index >= operands.size()) {
            throw new ArrayIndexOutOfBoundsException("Node index too high!");
        }
        operands.set(index, eqnAstNode);
    }

    public void addOperand(EqnAstNode eqnAstNode) throws EqnException {
        operands.add(eqnAstNode);
    }

    protected void simplifyChildren() throws EqnException {
        for (int i = 0; i != operands.size(); ++i) {
            setNodeAt(i, getNodeAt(i).simplify());
        }
    }

    protected int arity() {
        return operands.size();
    }

    protected abstract double evaluate() throws EqnException;

    public abstract EqnAstNode simplify() throws EqnException;

    public enum Type {
        Constant, Variable, BinaryOperation, Addition, Multiplication, Division, Power, UnaryOperation, PreDefinedUnaryFunction, CustomUnaryFunction, PreDefinedFunction, CustomFunction, Error
    }

    public enum PrecedenceType {
        UnaryMinus, Addition, Multiplication, Power, Function, Terminal
    }
}
