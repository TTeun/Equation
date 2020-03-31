package eqn.ast

import eqn.ast.EqnAstNode
import eqn.parser.exception.EqnException

class EqnAst(root: EqnAstNode) {
    private var root: EqnAstNode?

    @Throws(EqnException::class)
    fun simplify() {
        root = root!!.simplify()
        if (root!!.type == EqnAstNode.Type.Constant) {
            root = EqnAstNodeDouble(root!!.value.toDouble())
        }
    }

    override fun toString(): String {
        return "EquationAst{" +
                "root=" + root +
                '}'
    }

    @Throws(EqnException::class)
    fun evaluate(): Double {
        return root!!.evaluate()
    }

    init {
        this.root = root
    }
}