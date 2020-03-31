package eqn.parser

import eqn.ast.*
import eqn.ast.EqnAstNodeUnary.Companion.create
import eqn.parser.exception.BadBodyException
import eqn.parser.exception.EqnException
import java.util.*
import java.util.regex.Pattern
import kotlin.math.max

class EqnBodyParser private constructor() {
    private val astNodes: Vector<EqnAstNode?> = Vector(0)
    private var replacementIndex = 0

    @Throws(EqnException::class)
    private fun parseEquationBodyInternal(equationBodyString: String): EqnAstNode? {
        var equationBodyString = equationBodyString
        while (equationBodyString.contains("[")) {
            val maximumBracketDepth = findMaximumDepth(equationBodyString, bracketDepthPattern)
            val endOfDeepestBracketMatcher = Pattern.compile("]_$maximumBracketDepth").matcher(equationBodyString)
            if (!endOfDeepestBracketMatcher.find()) {
                throw BadBodyException("Can't find end of deepest bracket!")
            }
            val shrunkEquationBody = equationBodyString.substring(0, endOfDeepestBracketMatcher.end())
            val startOfDeepestBracketMatcher = Pattern.compile("\\[_$maximumBracketDepth").matcher(shrunkEquationBody)
            if (!startOfDeepestBracketMatcher.find()) {
                throw BadBodyException("Can't find start of deepest bracket!")
            }
            val expressionInBrackets = equationBodyString.substring(startOfDeepestBracketMatcher.end(), endOfDeepestBracketMatcher.start())
            astNodes.add(parseEquationBodyNoBracketsInternal(expressionInBrackets))
            equationBodyString = equationBodyString.substring(0, startOfDeepestBracketMatcher.start()) + '<' + replacementIndex + equationBodyString.substring(endOfDeepestBracketMatcher.end())
            ++replacementIndex
        }
        return parseEquationBodyNoBracketsInternal(equationBodyString)
    }

    @Throws(EqnException::class)
    private fun parseEquationBodyNoBracketsInternal(equationBodyString: String): EqnAstNode? {
        var equationBodyString = equationBodyString
        while (equationBodyString.contains("@")) {
            val maximumFunctionDepth = findMaximumDepth(equationBodyString, FunctionDepthPattern)
            val endOfDeepestFunctionMatcher = Pattern.compile("@[a-zA-Z]+\\)_$maximumFunctionDepth").matcher(equationBodyString)
            if (!endOfDeepestFunctionMatcher.find()) {
                throw BadBodyException("Can't find end of deepest function!")
            }
            var shrunkEquationBody = equationBodyString.substring(0, endOfDeepestFunctionMatcher.end())
            val startOfDeepestFunctionMatcher = Pattern.compile("@[a-zA-Z]+\\(_$maximumFunctionDepth").matcher(shrunkEquationBody)
            if (!startOfDeepestFunctionMatcher.find()) {
                throw BadBodyException("Can't find start of deepest function!")
            }
            shrunkEquationBody = equationBodyString.substring(startOfDeepestFunctionMatcher.start(), endOfDeepestFunctionMatcher.end())
            val functionNameMatcher = Pattern.compile("[a-zA-Z]+").matcher(shrunkEquationBody)
            if (!functionNameMatcher.find()) {
                throw BadBodyException("Can't find function name!")
            }
            val functionName = functionNameMatcher.group()
            val parameters = equationBodyString.substring(startOfDeepestFunctionMatcher.end(), endOfDeepestFunctionMatcher.start()).split(",".toRegex()).toTypedArray()
            if (parameters.size == 1) {
                if (Pattern.compile("sin|cos|tan|exp").matcher(functionName).matches()) {
                    astNodes.add(create(functionName, parseEquationBodyNoBracketsNoFunctionsInternal(parameters[0])!!))
                } else {
                    astNodes.add(EqnAstNodeCustomUnaryFunction(functionName, parseEquationBodyNoBracketsNoFunctionsInternal(parameters[0])!!))
                }
            } else {
                val equationNode = EqnAstNodeCustomFunction(functionName)
                for (i in parameters.indices) {
                    equationNode.addOperand(parseEquationBodyNoBracketsNoFunctionsInternal(parameters[i])!!)
                }
                astNodes.add(equationNode)
            }
            equationBodyString = equationBodyString.substring(0, startOfDeepestFunctionMatcher.start()) + '<' + replacementIndex + equationBodyString.substring(endOfDeepestFunctionMatcher.end())
            ++replacementIndex
        }
        return parseEquationBodyNoBracketsNoFunctionsInternal(equationBodyString)
    }

    @Throws(EqnException::class)
    private fun parseEquationBodyNoBracketsNoFunctionsInternal(equationBodyString: String): EqnAstNode? {
        var equationBodyString = equationBodyString
        var powerMatcher = powerPattern.matcher(equationBodyString)
        while (powerMatcher.find()) {
            val match = equationBodyString.substring(powerMatcher.start(), powerMatcher.end())
            val splitString = match.split("\\^".toRegex()).toTypedArray()
            equationBodyString = equationBodyString.substring(0, powerMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(powerMatcher.end())
            astNodes.add(EqnAstNodePower(stringToNode(splitString[0])!!, stringToNode(splitString[1])!!))
            powerMatcher.reset()
            powerMatcher = powerPattern.matcher(equationBodyString)
            ++replacementIndex
        }
        var multiplyMatcher = multiplyPattern.matcher(equationBodyString)
        while (multiplyMatcher.find()) {
            val match = equationBodyString.substring(multiplyMatcher.start(), multiplyMatcher.end())
            val splitString = match.split("[*/]".toRegex()).toTypedArray()
            equationBodyString = equationBodyString.substring(0, multiplyMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(multiplyMatcher.end())
            if (match.contains("*")) {
                astNodes.add(EqnAstNodeMultiply(stringToNode(splitString[0])!!, stringToNode(splitString[1])!!))
            } else {
                astNodes.add(EqnAstNodeDivide(stringToNode(splitString[0])!!, stringToNode(splitString[1])!!))
            }
            ++replacementIndex
            multiplyMatcher.reset()
            multiplyMatcher = multiplyPattern.matcher(equationBodyString)
        }
        var unaryMinusMatcher = Pattern.compile("-$valueString").matcher(equationBodyString)
        while (unaryMinusMatcher.lookingAt()) {
            val operand = equationBodyString.substring(unaryMinusMatcher.start() + 1, unaryMinusMatcher.end())
            equationBodyString = equationBodyString.substring(0, unaryMinusMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(unaryMinusMatcher.end())
            astNodes.add(EqnAstNodeUnaryMinus(stringToNode(operand)!!))
            unaryMinusMatcher.reset()
            unaryMinusMatcher = Pattern.compile("-$valueString").matcher(equationBodyString)
            ++replacementIndex
        }
        var addMatcher = addPattern.matcher(equationBodyString)
        while (addMatcher.find()) {
            val match = equationBodyString.substring(addMatcher.start(), addMatcher.end())
            val splitString = match.split("[+-]".toRegex()).toTypedArray()
            equationBodyString = equationBodyString.substring(0, addMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(addMatcher.end())
            if (match.contains("+")) {
                astNodes.add(EqnAstNodeAdd(stringToNode(splitString[0])!!, stringToNode(splitString[1])!!))
            } else {
                astNodes.add(EqnAstNodeSubtract(stringToNode(splitString[0])!!, stringToNode(splitString[1])!!))
            }
            ++replacementIndex
            addMatcher.reset()
            addMatcher = addPattern.matcher(equationBodyString)
        }
        val valueMatcher = valuePattern.matcher(equationBodyString)
        return if (valueMatcher.matches()) {
            stringToNode(valueMatcher.group())
        } else {
            throw BadBodyException("Couldn't resolve bracket-less and function-less expression")
        }
    }

    @Throws(BadBodyException::class)
    private fun stringToNode(nodeString: String): EqnAstNode? {
        when (nodeString[0]) {
            '$' -> return EqnAstNodeVariable(nodeString.substring(1))
            '#' -> return EqnAstNodeDouble(nodeString.substring(1))
            '<' -> {
                val indexOfNode = nodeString.substring(1).toInt()
                if (astNodes.elementAt(indexOfNode) == null) {
                    throw BadBodyException("astNodes at index is null, shouldn't happen!")
                }
                return astNodes.elementAt(indexOfNode)
            }
        }
        throw BadBodyException("Unexpected symbol")
    }

    companion object {
        private const val numberString = "(#[0-9.]+)"
        private const val variableString = "(\\$[a-zA-Z]+)"
        private const val replacedString = "(<[0-9]+)"
        private const val valueString = "($numberString|$variableString|$replacedString)"
        private val bracketDepthPattern = Pattern.compile("\\[_[0-9]+")
        private val FunctionDepthPattern = Pattern.compile("\\(_[0-9]+")
        private val valuePattern = Pattern.compile(valueString)
        private val powerPattern = Pattern.compile("$valueString\\^$valueString")
        private val multiplyPattern = Pattern.compile("$valueString[*/]$valueString")
        private val addPattern = Pattern.compile("$valueString[+-]$valueString")

        @Throws(EqnException::class)
        fun parseEquationBody(equationBodyString: String): EqnAst {
            return EqnAst(EqnBodyParser().parseEquationBodyInternal(equationBodyString)!!)
        }

        @Throws(EqnException::class)
        private fun findMaximumDepth(equationBodyString: String, pattern: Pattern): Int {
            var maximumBracketDepthSoFar = 0
            val bracketDepthMatcher = pattern.matcher(equationBodyString)
            while (bracketDepthMatcher.find()) {
                maximumBracketDepthSoFar = try {
                    max(maximumBracketDepthSoFar, bracketDepthMatcher.group().substring(2).toInt())
                } catch (e: Exception) {
                    throw EqnException("Could not convert bracket depth identifier to int", BadBodyException(e))
                }
            }
            return maximumBracketDepthSoFar
        }
    }

}