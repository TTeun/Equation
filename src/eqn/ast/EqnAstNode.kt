package eqn.ast

import eqn.parser.exception.EqnException

abstract class EqnAstNode {
    val type: Type
    val value: String
    val precedenceType: PrecedenceType

    constructor(precedenceType: PrecedenceType) {
        this.precedenceType = precedenceType
        value = "Empty"
        type = Type.Error
    }

    constructor(value: String, type: Type, precedenceType: PrecedenceType) {
        this.value = value
        this.type = type
        this.precedenceType = precedenceType
    }


    abstract fun getConstantValue(): Double

    open fun getNodeAt(index: Int): EqnAstNode {
        throw java.lang.UnsupportedOperationException()
    }

    open fun setNodeAt(index: Int, eqnAstNode: EqnAstNode) {
        throw java.lang.UnsupportedOperationException()
    }

    @Throws(EqnException::class)
    open fun addOperand(eqnAstNode: EqnAstNode) {
        throw java.lang.UnsupportedOperationException()
    }

    @Throws(EqnException::class)
    protected open fun simplifyChildren() {
        throw java.lang.UnsupportedOperationException()
    }

    @Throws(EqnException::class)
    abstract fun evaluate(): Double

    abstract fun arity(): Int

    @Throws(EqnException::class)
    abstract fun simplify(): EqnAstNode

    enum class Type {
        Constant, Variable, Subtraction, Addition, Multiplication, Division, Fraction, Power, UnaryOperation, PreDefinedUnaryFunction, CustomUnaryFunction, PreDefinedFunction, CustomFunction, Error
    }

    enum class PrecedenceType {
        UnaryMinus, Addition, Multiplication, Division, Power, Function, Terminal
    }
}