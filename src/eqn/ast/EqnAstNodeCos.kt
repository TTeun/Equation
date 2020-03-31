package eqn.ast

import eqn.parser.exception.EqnException
import kotlin.math.cos

class EqnAstNodeCos(operand: EqnAstNode) : EqnAstNodeUnary("cos", Type.PreDefinedUnaryFunction, PrecedenceType.Function, operand) {

    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return cos(operand.evaluate())
    }
}