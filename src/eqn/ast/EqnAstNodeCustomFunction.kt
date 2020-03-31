package eqn.ast

import eqn.parser.exception.EqnException

class EqnAstNodeCustomFunction(value: String) : EqnAstNodeArbitraryArity(value, Type.CustomFunction, PrecedenceType.Function) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        throw EqnException("Custom function evaluate not yet implemented (make this class abstract)!")
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode {
        for (i in 0 until arity()) {
            operands[i] = operands.elementAt(i).simplify()
        }
        return this
    }
}