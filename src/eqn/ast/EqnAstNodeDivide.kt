package eqn.ast

import eqn.parser.exception.EqnException

class EqnAstNodeDivide(leftOperand: EqnAstNode, rightOperand: EqnAstNode) : EqnAstNodeBinary("/", Type.Division, PrecedenceType.Multiplication, leftOperand, rightOperand) {
    @Throws(EqnException::class)
    override fun evaluate(): Double {
        return left.evaluate() / right.evaluate()
    }
}