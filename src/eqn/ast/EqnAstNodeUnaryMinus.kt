package eqn.ast

import eqn.parser.exception.EqnException

class EqnAstNodeUnaryMinus(operand: EqnAstNode) : EqnAstNodeUnary("-", Type.UnaryOperation, PrecedenceType.UnaryMinus, operand) {
    override fun toString(): String = "(-$operand)"

    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return -1.0 * operand.evaluate()
    }
}