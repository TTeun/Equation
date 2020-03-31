package eqn.ast

import eqn.parser.exception.EqnException

class EqnAstNodeDivide(leftOperand: EqnAstNode?, rightOperand: EqnAstNode?) : EqnAstNodeBinaryOperation("/", leftOperand, rightOperand, PrecedenceType.Multiplication, Type.Division) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return left().evaluate() / right().evaluate()
    }
}