package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeBinary
import eqn.parser.exception.EqnException
import kotlin.math.pow

class EqnAstNodePower(leftOperand: EqnAstNode, rightOperand: EqnAstNode) : EqnAstNodeBinary("^", Type.Power, PrecedenceType.Power, leftOperand, rightOperand) {

    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        return left.evaluate(arguments).pow(right.evaluate(arguments))
    }

    @Throws(EqnException::class)
    override fun simplify(arguments: Map<String, Double>?): EqnAstNode {
        simplifyChildren(arguments)
        if (right.type == Type.Constant) {
            if (right.value.toDouble() == 0.0) {
                return EqnAstNodeDouble(1.0)
            }
            if (right.value.toDouble() == 1.0) {
                return left
            }
        }
        if (left.type == Type.Constant) {
            if (left.value.toDouble() == 1.0) {
                return EqnAstNodeDouble(1.0)
            }
        }
        return super.simplify(arguments)
    }
}