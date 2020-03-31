package eqn.ast

import eqn.parser.exception.EqnException
import kotlin.math.cos

class EqnAstNodeCos(equationNode: EqnAstNode) : EqnAstNodePreDefinedUnaryFunction("cos", equationNode) {
    @Throws(EqnException::class)
     override fun evaluate(): Double {
        return cos(operand()!!.evaluate())
    }
}