package eqn.ast

import eqn.parser.exception.EqnException

class EqnAstNodeUnaryMinus(operand: EqnAstNode?) : EqnAstNodeUnaryFunction("-", Type.UnaryOperation, operand!!, PrecedenceType.UnaryMinus) {
    override fun toString(): String {
        return "-" + operand().toString()
    }

    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return -1.0 * operand()!!.evaluate()
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode? {
        simplifyChildren()
        return if (operand()!!.type == Type.Constant) {
            EqnAstNodeDouble(evaluate())
        } else this
    }
}