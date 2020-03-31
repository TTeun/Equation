package eqn

import eqn.ast.EqnAst
import eqn.parser.exception.EqnException

class Eqn(private val equationHead: EqnHead, private val equationAst: EqnAst) {
    @Throws(EqnException::class)
    fun evaluate(): Double {
        return equationAst.evaluate()
    }

    override fun toString(): String {
        return "Equation created:\n\t$equationHead\n\t$equationAst"
    }


    init {
        equationAst.simplify()
        println("Equation created:\n\t$equationHead\n\t$equationAst")
    }
}