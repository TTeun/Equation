package eqn.ast.base

import eqn.parser.exception.EqnException

abstract class EqnAstNode {
    val value: String
    val type: Type
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
    protected open fun simplifyChildren(arguments: Map<String, Double>?) {
        throw java.lang.UnsupportedOperationException()
    }

    @Throws(EqnException::class)
    abstract fun evaluate(arguments: Map<String, Double>?): Double

    abstract fun arity(): Int

    @Throws(EqnException::class)
    abstract fun simplify(arguments: Map<String, Double>?): EqnAstNode

    enum class Type {
        Constant, Variable, Subtraction, Addition, Multiplication, Fraction, Power, UnaryOperation, PreDefinedUnaryFunction, CustomUnaryFunction, PreDefinedFunction, CustomFunction, Error
    }

    enum class PrecedenceType {
        UnaryMinus, Addition, Multiplication, Division, Power, Function, Terminal
    }
}