package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeArbitraryArity
import eqn.ast.base.EqnAstNodeComparator
import eqn.parser.exception.EqnException

class EqnAstNodeMultiply(private var constant: Double = 1.0) : EqnAstNodeArbitraryArity("Add", Type.Multiplication, PrecedenceType.Multiplication) {

    override fun getConstantValue() = constant

    constructor(left: EqnAstNode, right: EqnAstNode) : this() {
        addOperand(left)
        addOperand(right)
    }

    fun negate() {
        constant *= -1.0
    }

    private fun multiply(multiplicand: Double) {
        constant *= multiplicand
    }

    @Throws(EqnException::class)
    override fun addOperand(operand: EqnAstNode) {
        when (operand.type) {
            Type.Constant -> this.multiply(operand.evaluate(null))
            Type.Multiplication -> {
                val multiplier: EqnAstNodeMultiply = operand as EqnAstNodeMultiply
                constant *= multiplier.constant
                for (i in 0 until multiplier.arity()) {
                    addOperand(multiplier.getNodeAt(i))
                }
            }
            else -> operands.add(operand)
        }
    }

    @Throws(EqnException::class)
    override fun evaluate(arguments: Map<String, Double>?): Double {
        var result = constant
        for (i in operands.indices) {
            result *= operands.elementAt(i).evaluate(arguments)
        }
        return result
    }

    override fun toString(): String {
        val stringBuffer = StringBuffer(if (constant == 1.0) "" else "$constant*")
        if (operands.size != 0) {
            for (i in operands.indices) {
                if (operands[i].precedenceType == PrecedenceType.Addition) {
                    stringBuffer.append('(' + operands.elementAt(i).toString() + ")*")
                } else {
                    stringBuffer.append(operands.elementAt(i).toString() + "*")
                }
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
        val comparator = EqnAstNodeComparator()

        operands.sortWith(comparator)
        val updatedOperands = ArrayList<EqnAstNode>()
        for (i in operands.indices) {
            if (operands.elementAt(i).type == Type.Constant) {
                constant *= operands.elementAt(i).evaluate(arguments)
            } else {
                updatedOperands.add(operands.elementAt(i))
            }
        }
        operands = updatedOperands
        return if (operands.size == 0) {
            EqnAstNodeDouble(constant)
        } else {
            for (i in operands.indices) {
                if (operands[i].type == Type.Fraction) {
                    return toFraction()
                }
            }

            this
        }
    }

    private fun toFraction(): EqnAstNodeFraction {
        val fraction = EqnAstNodeFraction()
        for (i in operands.indices) {
            fraction.addToNumerator(operands[i])
        }
        fraction.addToNumerator(EqnAstNodeDouble(constant))
        return fraction
    }
}