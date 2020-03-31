package eqn.ast

abstract class EqnAstNodeUnaryFunction(value: String, type: Type, operand: EqnAstNode, precedenceType: PrecedenceType) : EqnAstNodeUnary(value, type, precedenceType, operand)