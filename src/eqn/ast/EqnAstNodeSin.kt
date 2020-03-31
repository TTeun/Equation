package eqn.ast

import eqn.parser.exception.EqnException
import kotlin.math.sin

class EqnAstNodeSin(operand: EqnAstNode) : EqnAstNodeUnary("sin", Type.PreDefinedUnaryFunction, PrecedenceType.Function, operand) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return sin(operand.evaluate())
    }
}