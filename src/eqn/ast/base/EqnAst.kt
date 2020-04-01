package eqn.ast.base

import eqn.ast.EqnAstNodeDouble
import eqn.parser.exception.EqnException

class EqnAst(private var root: EqnAstNode) {
    @Throws(EqnException::class)
    fun simplify(arguments: Map<String, Double>?) {
        root = root.simplify(arguments)
        if (root.type == EqnAstNode.Type.Constant) {
            root = EqnAstNodeDouble(root.value.toDouble())
        }
    }

    override fun toString(): String {
        return "EquationAst{" +
                "root=" + root +
                '}'
    }

    @Throws(EqnException::class)
    fun evaluate(arguments: Map<String, Double>?): Double = root.evaluate(arguments)
}