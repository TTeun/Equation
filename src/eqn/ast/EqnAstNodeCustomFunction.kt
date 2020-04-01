package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeArbitraryArity
import eqn.parser.exception.EqnException

class EqnAstNodeCustomFunction(value: String) : EqnAstNodeArbitraryArity(value, Type.CustomFunction, PrecedenceType.Function) {
    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        throw EqnException("Custom function evaluate not yet implemented (make this class abstract)!")
    }

    @Throws(EqnException::class)
    override fun simplify(arguments: Map<String, Double>?): EqnAstNode {
        for (i in 0 until arity()) {
            operands[i] = operands.elementAt(i).simplify(arguments)
        }
        return this
    }
}