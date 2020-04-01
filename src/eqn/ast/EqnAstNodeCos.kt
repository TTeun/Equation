package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeUnary
import eqn.parser.exception.EqnException
import kotlin.math.cos

class EqnAstNodeCos(operand: EqnAstNode) : EqnAstNodeUnary("cos", Type.PreDefinedUnaryFunction, PrecedenceType.Function, operand) {

    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        return cos(operand.evaluate(arguments))
    }
}