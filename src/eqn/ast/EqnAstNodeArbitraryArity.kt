package eqn.ast

import java.security.InvalidParameterException
import java.util.*

abstract class EqnAstNodeArbitraryArity(value: String, type: Type, precedenceType: PrecedenceType, protected var operands: Vector<EqnAstNode> = Vector())
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
            operands.setElementAt(operands[i].simplify(), i)
        }
    }

    override fun addOperand(operand: EqnAstNode) {
        operands.addElement(operand)
    }

    override fun arity() = operands.size
}