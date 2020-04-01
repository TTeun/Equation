package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeUnary
import eqn.parser.exception.EqnException
import kotlin.math.tan

class EqnAstNodeTan(operand: EqnAstNode) : EqnAstNodeUnary("tan", Type.PreDefinedUnaryFunction, PrecedenceType.Function, operand) {
    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        return tan(operand.evaluate(arguments))
    }
}