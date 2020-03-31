package eqn.ast

import eqn.parser.exception.EqnException
import java.util.*

abstract class EqnAstNodePreDefinedFunction(value: String) : EqnAstNode(value, Type.PreDefinedFunction, PrecedenceType.Function) {
    override fun arity(): Int {
        return operands.size
    }

    @Throws(EqnException::class)
    override fun addOperand(operand: EqnAstNode) {
        operands.add(operand)
    }

    override fun toString(): String {
        val stringBuffer = StringBuffer("$value(")
        for (i in 0 until operands.size - 1) {
            stringBuffer.append(operands.elementAt(i).toString() + ", ")
        }
        stringBuffer.append(operands.lastElement().toString())
        stringBuffer.append(")")
        return String(stringBuffer)
    }

    init {
        operands = Vector(0)
    }
}