package eqn.ast

import eqn.parser.exception.EqnException
import java.security.InvalidParameterException

abstract class EqnAstNodeBinary(value: String, type: Type, precedenceType: PrecedenceType, var left: EqnAstNode, var right: EqnAstNode) : EqnAstNode(value, type, precedenceType) {
    override fun getConstantValue(): Double {
        throw UnsupportedOperationException("getConstantValue in EqnAstNodeBinary not supported")
    }

    override fun simplifyChildren() {
        left = left.simplify()
        right = right.simplify()
    }

    override fun toString(): String {
        if (left.precedenceType == PrecedenceType.Multiplication && this.precedenceType == PrecedenceType.Division) {
            if (right.precedenceType == PrecedenceType.Terminal) {
                return left.toString() + value + right.toString()
            }
            return "$left$value($right)"
        }

        return ((if (left.precedenceType >= precedenceType) left.toString() else "($left)")
                + value
                + if (right.precedenceType >= precedenceType) right.toString() else "($right)")
    }

    override fun getNodeAt(index: Int): EqnAstNode {
        return when (index) {
            0 -> left
            1 -> right
            else -> throw InvalidParameterException("index for getNode in EqnAstUnary should always be 0")
        }
    }

    override fun arity(): Int = 2

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode {
        simplifyChildren()
        return if (left.type == Type.Constant && right.type == Type.Constant) {
            EqnAstNodeDouble(evaluate())
        } else this
    }

}