package eqn.ast

import eqn.parser.exception.EqnException

abstract class EqnAstNodeBinaryOperation(value: String, leftOperand: EqnAstNode?, rightOperand: EqnAstNode?, precedenceType: PrecedenceType, type: Type) : EqnAstNode(value, type, precedenceType) {
    fun left(): EqnAstNode {
        return getNodeAt(0)
    }

    fun right(): EqnAstNode {
        return getNodeAt(1)
    }

    override fun toString(): String {
        if (left().precedenceType == PrecedenceType.Multiplication && this.precedenceType == PrecedenceType.Division) {
            if (right().precedenceType == PrecedenceType.Terminal) {
                return left().toString() + value + right().toString()
            }
            return left().toString() + value + '(' + right().toString() + ')'
        }

        return ((if (left().precedenceType >= precedenceType) left().toString() else "(" + left().toString() + ")")
                + value
                + if (right().precedenceType >= precedenceType) right().toString() else "(" + right().toString() + ")")
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode? {
        simplifyChildren()
        return if (left().type == Type.Constant && right().type == Type.Constant) {
            EqnAstNodeDouble(evaluate())
        } else this
    }

    init {
        operands.addElement(leftOperand)
        operands.addElement(rightOperand)
    }
}