package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeUnary
import eqn.parser.exception.EqnException

class EqnAstNodeCustomUnaryFunction(value: String, equationNode: EqnAstNode) : EqnAstNodeUnary(value, Type.CustomUnaryFunction, PrecedenceType.Function, equationNode) {
    override fun toString(): String {
        return "$value($operand)"
    }

    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        throw EqnException("Unary custom function evaluate not yet implemented (make this class abstract)!")
    }

    @Throws(EqnException::class)
    override fun simplify(arguments: Map<String, Double>?): EqnAstNode {
        simplifyChildren(arguments)
        return this
    }
}