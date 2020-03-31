package eqn.ast

import eqn.parser.exception.EqnException
import kotlin.math.sin

class EqnAstNodeSin(equationNode: EqnAstNode) : EqnAstNodePreDefinedUnaryFunction("sin", equationNode) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return sin(operand()!!.evaluate())
    }
}