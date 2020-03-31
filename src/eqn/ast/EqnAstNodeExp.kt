package eqn.ast

import eqn.parser.exception.EqnException
import kotlin.math.exp

class EqnAstNodeExp(equationNode: EqnAstNode) : EqnAstNodePreDefinedUnaryFunction("exp", equationNode) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return exp(operand()!!.evaluate())
    }
}