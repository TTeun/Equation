package eqn.ast

import eqn.ast.base.EqnAstNode
import eqn.ast.base.EqnAstNodeUnary

abstract class EqnAstNodeUnaryFunction(value: String, type: Type, operand: EqnAstNode, precedenceType: PrecedenceType) : EqnAstNodeUnary(value, type, precedenceType, operand)