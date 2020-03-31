package eqn.ast

import eqn.parser.exception.EqnException
import kotlin.math.tan

class EqnAstNodeTan(operand: EqnAstNode) : EqnAstNodeUnary("tan", Type.PreDefinedUnaryFunction, PrecedenceType.Function, operand) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return tan(operand.evaluate())
    }
}