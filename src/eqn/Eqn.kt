package eqn

import eqn.ast.base.EqnAst
import eqn.parser.exception.EqnException

class Eqn(private val equationHead: EqnHead, private val equationAst: EqnAst) {
    override fun toString(): String {
        return "Equation created:\n\t$equationHead\n\t$equationAst"
    }

    @Throws(EqnException::class)
    fun evaluate(arguments: Map<String, Double>?): Double {
        val variablePredicate: (String) -> Boolean = { equationHead.getVariableNames().contains(it) }
        val filteredArguments = arguments!!.filterKeys(variablePredicate)
        if (filteredArguments.size != equationHead.getVariableNames().size) {
            throw IllegalArgumentException("Trying to evaluate in Eqn without setting all variables!")
        }
        return equationAst.evaluate(filteredArguments)
    }

    fun simplify(arguments: Map<String, Double>?) {
        val variablePredicate: (String) -> Boolean = { equationHead.getVariableNames().contains(it) }
        val filteredArguments = arguments!!.filterKeys(variablePredicate)
        equationAst.simplify(filteredArguments)
    }

    init {
        equationAst.simplify(null)
        println("Equation created:\n\t$equationHead\n\t$equationAst")
    }
}