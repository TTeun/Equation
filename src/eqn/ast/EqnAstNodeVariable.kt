package eqn.ast

import eqn.ast.base.EqnAstNode

class EqnAstNodeVariable(value: String) : EqnAstNode(value, Type.Variable, PrecedenceType.Terminal) {

    override fun evaluate(arguments: Map<String, Double>?): Double {
        if (arguments == null) {
            throw IllegalArgumentException("Evaluating variable where variable is not assigned as value (EqnAstNodeVariable)")
        }
        if (arguments.containsKey(value)) {
            return arguments[value]!!
        }
        throw IllegalArgumentException("Evaluating variable where variable is not assigned as value (EqnAstNodeVariable)")
    }

    override fun getConstantValue(): Double {
        throw UnsupportedOperationException("getConstantValue in EqnAstNodeVariable not supported")
    }

    override fun toString(): String {
        return value
    }

    override fun simplify(arguments: Map<String, Double>?): EqnAstNode {
        if (arguments == null) {
            return this
        }
        if (arguments.containsKey(value)) {
            return EqnAstNodeDouble(arguments[value]!!)
        }
        return this
    }

    override fun arity(): Int = 0
}