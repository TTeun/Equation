package eqn.ast

import eqn.ast.base.EqnAstNode

class EqnAstNodeFraction() : EqnAstNode("/", Type.Fraction, PrecedenceType.Division) {
    private var numerator: EqnAstNode = EqnAstNodeDouble(1.0)
    private var denominator: EqnAstNode = EqnAstNodeDouble(1.0)

    private var numeratorWasChanged = false
    private var denominatorWasChanged = false

    constructor(numerator: EqnAstNode, denominator: EqnAstNode) : this() {
        addToNumerator(numerator)
        addToDenominator(denominator)
    }

    override fun getConstantValue(): Double {
        TODO("Not yet implemented")
    }

    override fun evaluate(arguments: Map<String, Double>?): Double {
        return numerator.evaluate(arguments) / denominator.evaluate(arguments)
    }

    override fun arity(): Int {
        TODO("Not yet implemented")
    }

    override fun simplify(arguments: Map<String, Double>?): EqnAstNode {
        numerator = numerator.simplify(arguments)
        denominator = denominator.simplify(arguments)
        return this
    }

    override fun toString(): String {
        if (numerator.precedenceType == PrecedenceType.Multiplication || numerator.precedenceType == PrecedenceType.Terminal) {
            if (denominator.precedenceType == PrecedenceType.Terminal) {
                return "$numerator/$denominator"
            }
            return "$numerator/($denominator)"
        }
        return "($numerator)/($denominator)"
    }

    fun addToNumerator(top: EqnAstNode) {
        val simplifiedTop = top.simplify(null)
        if (simplifiedTop.type == Type.Fraction) run {
            val topFraction: EqnAstNodeFraction = simplifiedTop as EqnAstNodeFraction
            this.addToNumerator(topFraction.numerator)
            this.addToDenominator(topFraction.denominator)
            return
        } else if (!numeratorWasChanged) {
            numerator = simplifiedTop
            numeratorWasChanged = true
            return
        }
        numeratorWasChanged = true
        numerator = EqnAstNodeMultiply(numerator, simplifiedTop)
    }

    private fun addToDenominator(bottom: EqnAstNode) {
        val simplifiedBottom = bottom.simplify(null)
        if (simplifiedBottom.type == Type.Fraction) run {
            val bottomFraction: EqnAstNodeFraction = simplifiedBottom as EqnAstNodeFraction
            this.addToNumerator(bottomFraction.denominator)
            this.addToDenominator(bottomFraction.numerator)
            return
        } else if (!denominatorWasChanged) {
            denominator = simplifiedBottom
            denominatorWasChanged = true
            return
        }
        denominatorWasChanged = true
        denominator = EqnAstNodeMultiply(denominator, simplifiedBottom)
    }
}