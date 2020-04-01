package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeArbitraryArity
import eqn.parser.exception.EqnException

abstract class EqnAstNodePreDefinedFunction(value: String) : EqnAstNodeArbitraryArity(value, Type.PreDefinedFunction, PrecedenceType.Function) {
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
        stringBuffer.append(operands.last().toString())
        stringBuffer.append(")")
        return String(stringBuffer)
    }
}