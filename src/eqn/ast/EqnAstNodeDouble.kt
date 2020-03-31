package eqn.ast

class EqnAstNodeDouble : EqnAstNode {
    private var valueOfNode: Double

    override fun getConstantValue(): Double {
        throw UnsupportedOperationException("getConstantValue in EqnAstNodeDouble not supported")
    }

    constructor(value: String) : super(value, Type.Constant, PrecedenceType.Terminal) {
        valueOfNode = value.toDouble()
    }

    constructor(value: Double) : super(value.toString(), Type.Constant, PrecedenceType.Terminal) {
        valueOfNode = value
    }

    override fun toString(): String {
        return valueOfNode.toString()
    }

    override fun evaluate(): Double {
        return valueOfNode
    }

    override fun arity(): Int = 0

    override fun simplify(): EqnAstNode {
        return this
    }
}