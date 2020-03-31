package eqn.ast

import eqn.parser.exception.EqnException

class EqnAstNodeCustomUnaryFunction(value: String, equationNode: EqnAstNode) : EqnAstNodeUnaryFunction(value, Type.CustomUnaryFunction, equationNode, PrecedenceType.Function) {
    override fun toString(): String {
        return value + '(' + operand().toString() + ')'
    }

    @Throws(EqnException::class)
     override fun evaluate(): Double {
        throw EqnException("Unary custom function evaluate not yet implemented (make this class abstract)!")
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode? {
        simplifyChildren()
        return this
    }
}