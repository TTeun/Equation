package eqn.ast

import eqn.parser.exception.EqnException
import kotlin.math.tan

class EqnAstNodeTan(equationNode: EqnAstNode) : EqnAstNodePreDefinedUnaryFunction("tan", equationNode) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return tan(operand()!!.evaluate())
    }
}