package eqn.ast

abstract class EqnAstNodeUnaryFunction(value: String, type: Type, equationNode: EqnAstNode, precedenceType: PrecedenceType) : EqnAstNode(value, type, precedenceType) {
    fun operand(): EqnAstNode? {
        return getNodeAt(0)
    }

    init {
        operands.addElement(equationNode)
    }
}