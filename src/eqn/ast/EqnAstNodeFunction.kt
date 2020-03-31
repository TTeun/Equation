package eqn.ast

abstract class EqnAstNodeFunction : EqnAstNode {
    constructor(precedenceType: PrecedenceType) : super(precedenceType)
    constructor(value: String, type: Type, precedenceType: PrecedenceType) : super(value, type, precedenceType)

    override fun arity(): Int {
        return operands.size
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
}