package eqn.ast

import java.security.InvalidParameterException

abstract class EqnAstNodeArbitraryArity(value: String, type: Type, precedenceType: PrecedenceType, protected var operands: ArrayList<EqnAstNode> = ArrayList())
    : EqnAstNode(value, type, precedenceType) {

    override fun getConstantValue(): Double {
        throw UnsupportedOperationException("getConstantValue in EqnAstNodeArbitraryArity not supported")
    }

    override fun getNodeAt(index: Int): EqnAstNode {
        if (index >= operands.size) {
            throw InvalidParameterException("Index exceeds operands.size() in EqnAstNodeArbitraryArity")
        }
        return operands[index]
    }

    override fun simplifyChildren() {
        for (i in operands.indices) {
            operands[i] = operands[i].simplify()
        }
    }

    override fun addOperand(operand: EqnAstNode) {
        operands.add(operand)
    }

    override fun arity() = operands.size
}