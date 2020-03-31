package eqn.ast

import eqn.parser.exception.EqnException
import java.util.*

class EqnAstNodeMultiply() : EqnAstNodeArbitraryArity("Add", Type.Multiplication, PrecedenceType.Multiplication) {
    private var constant: Double = 1.0

    override fun getConstantValue() = constant

    constructor(left: EqnAstNode, right: EqnAstNode) : this() {
        addOperand(left)
        addOperand(right)
    }

    private fun multiply(multiplicand: Double) {
        constant *= multiplicand
    }

    @Throws(EqnException::class)
    override fun addOperand(operand: EqnAstNode) {
        when (operand.type) {
            Type.Constant -> this.multiply(operand.evaluate())
            Type.Multiplication -> {
                constant *= operand.getConstantValue()
                for (i in 0 until operand.arity()) {
                    addOperand(operand.getNodeAt(i))
                }
            }
            else -> operands.add(operand)
        }
    }

    @Throws(EqnException::class)
    override fun evaluate(): Double {
        var result = constant
        for (i in operands.indices) {
            result *= operands.elementAt(i).evaluate()
        }
        return result
    }

    override fun toString(): String {
        val stringBuffer = StringBuffer(if (constant == 1.0) "" else "$constant*")
        if (operands.size != 0) {
            for (i in operands.indices) {
                stringBuffer.append(operands.elementAt(i).toString() + "*")
            }
        }
        if (stringBuffer.isNotEmpty()) {
            stringBuffer.setLength(stringBuffer.length - 1)
        }
        return String(stringBuffer)
    }

    @Throws(EqnException::class)
    override fun simplify(): EqnAstNode {
        for (i in operands.indices) {
            operands[i] = operands.elementAt(i).simplify()
        }
        val updatedOperands = Vector<EqnAstNode>()
        for (i in operands.indices) {
            if (operands.elementAt(i).type == Type.Constant) {
                constant *= operands.elementAt(i).evaluate()
            } else {
                updatedOperands.add(operands.elementAt(i))
            }
        }
        operands = updatedOperands
        return if (operands.size == 0) {
            EqnAstNodeDouble(constant)
        } else this
    }

//    private fun simplifyToFraction(): EqnAstNodeDivide {
//        val numerator = EqnAstNodeMultiply()
//        val denominator = EqnAstNodeMultiply()
//
//        for (i in operands.indices) {
//            if (operands[i].type == Type.Division) {
//                denominator.addOperand(operands[i])
//            } else {
//                numerator.addOperand(operands[i])
//            }
//        }
//
//        return EqnAstNodeDivide(numerator, denominator)
//    }
}