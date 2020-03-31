package eqn.ast;

import eqn.parser.exception.EqnException;

public class EqnAstNodeAdd extends EqnAstNodeBinaryOperation {

    private EqnAstNodeAdd(String value, EqnAstNode leftOperand, EqnAstNode rightOperand) throws EqnException {
        super(value, leftOperand, rightOperand, PrecedenceType.Addition);
        System.out.println("asdsaddasd");
    }

    @Override
    public double evaluate() throws EqnException {
        return left().evaluate() + right().evaluate();
    }

    @Override
    public EqnAstNode simplify() throws EqnException {
        simplifyChildren();
        if (left().type == Type.Constant && Double.parseDouble(left().value) == 0.0) {
            return right();
        }
        if (right().type == Type.Constant && Double.parseDouble(right().value) == 0.0) {
            return left();
        }
        if (right().precedenceType != PrecedenceType.Addition && left().precedenceType == PrecedenceType.Addition) {
            return new EqnAstNodeAdd(value, right(), left()).simplify();
        }

        return super.simplify();
    }
}
