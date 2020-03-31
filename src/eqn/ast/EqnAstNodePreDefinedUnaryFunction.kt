package eqn.ast

import eqn.parser.exception.EqnException

abstract class EqnAstNodePreDefinedUnaryFunction(value: String, equationNode: EqnAstNode) : EqnAstNodeUnaryFunction(value, Type.PreDefinedUnaryFunction, equationNode, PrecedenceType.Function) {
    override fun toString(): String {
        return value + '(' + operand().toString() + ')'
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode? {
        simplifyChildren()
        return if (operand()!!.type == Type.Constant) {
            EqnAstNodeDouble(evaluate())
        } else this
    }

    companion object {
        @kotlin.jvm.JvmStatic
        @Throws(EqnException::class)
        fun create(value: String, equationNode: EqnAstNode): EqnAstNodePreDefinedUnaryFunction {
            if (value == "sin") {
                return EqnAstNodeSin(equationNode)
            }
            if (value == "cos") {
                return EqnAstNodeCos(equationNode)
            }
            if (value == "tan") {
                return EqnAstNodeTan(equationNode)
            }
            if (value == "exp") {
                return EqnAstNodeExp(equationNode)
            }
            throw EqnException("PreDefinedUnaryFunction $value not yet implemented!")
        }
    }
}