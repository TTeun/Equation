package eqn.ast

import eqn.parser.exception.EqnException
import java.util.*

class EqnAstNodeAdd() : EqnAstNode("Add", Type.Addition, PrecedenceType.Addition) {
    override var constant = 0.0

    constructor(left: EqnAstNode, right: EqnAstNode) : this() {
        addOperand(left)
        addOperand(right)
    }

    @Throws(EqnException::class)
    override fun addOperand(eqnAstNode: EqnAstNode) {
        when (eqnAstNode.type) {
            Type.Constant -> constant += eqnAstNode.evaluate()
            Type.Addition -> {
                constant += eqnAstNode.constant
                for (i in eqnAstNode.operands.indices) {
                    addOperand(eqnAstNode.operands.elementAt(i))
                }
            }
            else -> operands.add(eqnAstNode)
        }
    }

    @Throws(EqnException::class)
    override fun evaluate(): Double {
        var result = constant
        for (i in operands.indices) {
            result += operands.elementAt(i).evaluate()
        }
        return result
    }

    override fun toString(): String {
        val stringBuffer = StringBuffer(if (constant == 1.0) "" else "$constant+")
        if (operands.size != 0) {
            for (i in operands.indices) {
                stringBuffer.append(operands.elementAt(i).toString() + "+")
            }
        }
        if (stringBuffer.isNotEmpty()) {
            stringBuffer.setLength(stringBuffer.length - 1)
        }
        return String(stringBuffer)
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode? {
        for (i in operands.indices) {
            operands[i] = operands.elementAt(i).simplify()
        }
        val updatedOperands = Vector<EqnAstNode>()
        for (i in operands.indices) {
            if (operands.elementAt(i).type == Type.Constant) {
                constant += operands.elementAt(i).evaluate()
            } else {
                updatedOperands.add(operands.elementAt(i))
            }
        }
        operands = updatedOperands
        return if (operands.size == 0) {
            EqnAstNodeDouble(constant)
        } else this
    }
}