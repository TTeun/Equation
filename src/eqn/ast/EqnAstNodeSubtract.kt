package eqn.ast

import eqn.parser.exception.EqnException

class EqnAstNodeSubtract(leftOperand: EqnAstNode?, rightOperand: EqnAstNode?) : EqnAstNodeBinaryOperation("-", leftOperand, rightOperand, PrecedenceType.Addition, Type.Addition) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return left().evaluate() - right().evaluate()
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode? {
        simplifyChildren()
        return EqnAstNodeAdd(left(), EqnAstNodeUnaryMinus(right())).simplify()
    }
}