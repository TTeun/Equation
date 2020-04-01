package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeBinary
import eqn.parser.exception.EqnException

class EqnAstNodeSubtract(leftOperand: EqnAstNode, rightOperand: EqnAstNode) : EqnAstNodeBinary("-", Type.Subtraction, PrecedenceType.Addition, leftOperand, rightOperand) {
    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        return left.evaluate(arguments) - right.evaluate(arguments)
    }

    @Throws(EqnException::class)
    override fun simplify(arguments: Map<String, Double>?): EqnAstNode {
        simplifyChildren(arguments)
        return EqnAstNodeAdd(left, EqnAstNodeUnaryMinus(right.simplify(arguments)))
    }
}