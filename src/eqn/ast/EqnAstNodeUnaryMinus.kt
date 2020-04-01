package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeUnary
import eqn.parser.exception.EqnException

class EqnAstNodeUnaryMinus(operand: EqnAstNode) : EqnAstNodeUnary("-", Type.UnaryOperation, PrecedenceType.UnaryMinus, operand) {
    override fun toString(): String = "(-$operand)"

    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        return -1.0 * operand.evaluate(arguments)
    }

    override fun simplify(arguments : Map<String,Double>?): EqnAstNode {
        if (operand.type == Type.Constant) {
            return EqnAstNodeDouble(-1.0 * (operand as EqnAstNodeDouble).evaluate(arguments))
        }
        return super.simplify(arguments)
    }
}