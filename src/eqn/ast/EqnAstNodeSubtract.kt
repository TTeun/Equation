package eqn.ast

import eqn.parser.exception.EqnException

class EqnAstNodeSubtract(leftOperand: EqnAstNode, rightOperand: EqnAstNode) : EqnAstNodeBinary("-", Type.Subtraction, PrecedenceType.Addition, leftOperand, rightOperand) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return left.evaluate() - right.evaluate()
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode {
        simplifyChildren()
        return EqnAstNodeAdd(left, EqnAstNodeUnaryMinus(right.simplify()))
    }
}