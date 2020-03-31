package eqn.ast;

abstract public class EqnAstNodeFunction extends EqnAstNode {
    public EqnAstNodeFunction(PrecedenceType precedenceType) {
        super(precedenceType);
    }

    public EqnAstNodeFunction(String value, Type type, PrecedenceType precedenceType) {
        super(value, type, precedenceType);
    }

    protected int arity() {
        return operands.size();
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
