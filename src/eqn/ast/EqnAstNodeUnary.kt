package eqn.ast

import eqn.parser.exception.EqnException
import java.security.InvalidParameterException

abstract class EqnAstNodeUnary(value: String, type: Type, precedenceType: PrecedenceType, var operand: EqnAstNode) : EqnAstNode(value, type, precedenceType) {

    override fun getConstantValue(): Double {
        throw UnsupportedOperationException("getConstantValue in EqnAstNodeUnary not supported")
    }

    override fun simplifyChildren() {
        operand = operand.simplify()
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode {
        simplifyChildren()
        return if (operand.type == Type.Constant) {
            EqnAstNodeDouble(evaluate())
        } else this
    }

    override fun getNodeAt(index: Int): EqnAstNode {
        if (index > 0) {
            throw InvalidParameterException("index for getNode in EqnAstUnary should always be 0")
        }
        return operand
    }

    override fun arity(): Int = 1

    companion object {
        @kotlin.jvm.JvmStatic
        @Throws(EqnException::class)
        fun create(value: String, equationNode: EqnAstNode): EqnAstNodeUnary {
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