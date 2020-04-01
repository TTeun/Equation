package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeArbitraryArity
import eqn.parser.exception.EqnException

class EqnAstNodeAdd(private var constant: Double = 0.0) : EqnAstNodeArbitraryArity("Add", Type.Addition, PrecedenceType.Addition) {
    override fun getConstantValue() = constant

    constructor(left: EqnAstNode, right: EqnAstNode) : this() {
        addOperand(left)
        addOperand(right)
    }

    @Throws(EqnException::class)
    override fun addOperand(operand: EqnAstNode) {
        val simplifiedOperand: EqnAstNode = operand.simplify(null)
        when (simplifiedOperand.type) {
            Type.Constant -> constant += simplifiedOperand.evaluate(null)
            Type.Addition -> {
                constant += simplifiedOperand.getConstantValue()
                for (i in 0 until simplifiedOperand.arity()) {
                    addOperand(simplifiedOperand.getNodeAt(i))
                }
            }
            else -> operands.add(simplifiedOperand)
        }
    }

    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        var result = constant
        for (i in operands.indices) {
            result += operands.elementAt(i).evaluate(arguments)
        }
        return result
    }

    override fun toString(): String {
        val stringBuffer = StringBuffer(if (constant == 0.0) "" else "$constant+")
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
    override fun simplify(arguments: Map<String, Double>?): EqnAstNode {
        for (i in operands.indices) {
            operands[i] = operands.elementAt(i).simplify(arguments)
        }
        val updatedOperands = ArrayList<EqnAstNode>()
        for (i in operands.indices) {
            if (operands.elementAt(i).type == Type.Constant) {
                constant += operands.elementAt(i).evaluate(arguments)
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