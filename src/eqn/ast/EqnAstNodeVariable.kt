package eqn.ast

import eqn.parser.exception.EqnException

class EqnAstNodeVariable(value: String) : EqnAstNode(value, Type.Variable, PrecedenceType.Terminal) {

    override fun getConstantValue(): Double {
        throw UnsupportedOperationException("getConstantValue in EqnAstNodeVariable not supported")
    }

    override fun toString(): String {
        return value
    }

    @Throws(EqnException::class)
    override fun evaluate(): Double {
        throw EqnException("Evaluate for variable not yet implemented!")
    }

    override fun simplify(): EqnAstNode {
        return this
    }

    override fun arity(): Int = 0
}