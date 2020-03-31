package eqn.ast

import eqn.parser.exception.EqnException
import kotlin.math.exp

class EqnAstNodeExp(operand: EqnAstNode) : EqnAstNodeUnary("exp", Type.PreDefinedUnaryFunction, PrecedenceType.Function, operand) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return exp(operand.evaluate())
    }
}