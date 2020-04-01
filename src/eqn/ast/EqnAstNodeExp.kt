package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeUnary
import eqn.parser.exception.EqnException
import kotlin.math.exp

class EqnAstNodeExp(operand: EqnAstNode) : EqnAstNodeUnary("exp", Type.PreDefinedUnaryFunction, PrecedenceType.Function, operand) {
    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        return exp(operand.evaluate(arguments))
    }
}