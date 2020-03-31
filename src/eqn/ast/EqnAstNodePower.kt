package eqn.ast

import eqn.parser.exception.EqnException
import kotlin.math.pow

class EqnAstNodePower(leftOperand: EqnAstNode?, rightOperand: EqnAstNode?) : EqnAstNodeBinaryOperation("^", leftOperand, rightOperand, PrecedenceType.Power, Type.Power) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return left()!!.evaluate().pow(right()!!.evaluate())
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode? {
        simplifyChildren()
        if (right()!!.type == Type.Constant) {
            if (right()!!.value.toDouble() == 0.0) {
                return EqnAstNodeDouble(1.0)
            }
            if (right()!!.value.toDouble() == 1.0) {
                return left()
            }
        }
        if (left()!!.type == Type.Constant) {
            if (left()!!.value.toDouble() == 1.0) {
                return EqnAstNodeDouble(1.0)
            }
        }
        return super.simplify()
    }
}