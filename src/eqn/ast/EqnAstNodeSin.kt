package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeUnary
import eqn.parser.exception.EqnException
import kotlin.math.sin

class EqnAstNodeSin(operand: EqnAstNode) : EqnAstNodeUnary("sin", Type.PreDefinedUnaryFunction, PrecedenceType.Function, operand) {
    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        return sin(operand.evaluate(arguments))
    }
}