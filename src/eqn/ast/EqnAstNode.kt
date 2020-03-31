package eqn.ast

import eqn.parser.exception.EqnException
import java.util.*

abstract class EqnAstNode {
    val type: Type
    val value: String
    val precedenceType: PrecedenceType
    var operands: Vector<EqnAstNode>

    constructor(precedenceType: PrecedenceType) {
        this.precedenceType = precedenceType
        value = "Empty"
        type = Type.Error
        operands = Vector()
    }

    constructor(value: String, type: Type, precedenceType: PrecedenceType) {
        this.value = value
        this.type = type
        this.precedenceType = precedenceType
        operands = Vector()
    }

    open val constant: Double
        get() {
            throw UnsupportedOperationException()
        }

    open fun getNodeAt(index: Int): EqnAstNode {
        if (index >= operands.size) {
            throw ArrayIndexOutOfBoundsException("Node index too high!")
        }
        return operands.elementAt(index)
    }

    protected fun setNodeAt(index: Int, eqnAstNode: EqnAstNode?) {
        if (index >= operands.size) {
            throw ArrayIndexOutOfBoundsException("Node index too high!")
        }
        operands[index] = eqnAstNode
    }

    @Throws(EqnException::class)
    open fun addOperand(eqnAstNode: EqnAstNode) {
        operands.add(eqnAstNode)
    }

    @Throws(EqnException::class)
    protected fun simplifyChildren() {
        for (i in operands.indices) {
            setNodeAt(i, getNodeAt(i).simplify())
        }
    }

    protected open fun arity(): Int {
        return operands.size
    }

    @Throws(EqnException::class)
    abstract fun evaluate(): Double

    @Throws(EqnException::class)
    abstract fun simplify(): EqnAstNode?
    enum class Type {
        Constant, Variable, BinaryOperation, Addition, Multiplication, Division, Fraction, Power, UnaryOperation, PreDefinedUnaryFunction, CustomUnaryFunction, PreDefinedFunction, CustomFunction, Error
    }

    enum class PrecedenceType {
        UnaryMinus, Addition, Multiplication, Division, Power, Function, Terminal
    }
}