package eqn.parser

import eqn.EqnHead
import eqn.parser.exception.BadHeaderException
import java.util.regex.Pattern

object EqnHeadParser {
    private val functionNamePattern = Pattern.compile("@[a-zA-Z]+\\(_0")
    private val variableListPattern = Pattern.compile("(\\$[a-zA-Z]+,)*\\$[a-zA-Z]+")
    private val variablePattern = Pattern.compile("\\$[a-zA-Z]+")

    @Throws(BadHeaderException::class)
    fun parseEquationHead(equationHeadString: String): EqnHead {
        var equationHeadString = equationHeadString
        print("\u001b[1;37m")
        print("Parsing head: \t\t\t")
        equationHeadString = equationHeadString.trim { it <= ' ' }
        val functionNameMatcher = functionNamePattern.matcher(equationHeadString)
        if (!functionNameMatcher.find()) {
            throw BadHeaderException("Cannot discern name of function")
        }
        if (functionNameMatcher.start() != 0) {
            throw BadHeaderException("Start of header formatted incorrectly after pre-processing")
        }
        val functionName = functionNameMatcher.group().substring(1, functionNameMatcher.end() - 3)
        val equationHead = EqnHead(functionName)
        var variableListString = equationHeadString.substring(functionNameMatcher.end())
        val endOfVariableListIndex = variableListString.length - 4 - functionName.length
        if (variableListString.substring(endOfVariableListIndex) != "@$functionName)_0") {
            throw BadHeaderException("End of header formatted incorrectly after pre-processing")
        }
        variableListString = variableListString.substring(0, endOfVariableListIndex)
        val variableListMatcher = variableListPattern.matcher(variableListString)
        if (!variableListMatcher.matches()) {
            throw BadHeaderException("Formatting of arguments of function is incorrect")
        }
        val variableMatcher = variablePattern.matcher(variableListString)
        while (variableMatcher.find()) {
            equationHead.addVariableName(variableListString.substring(variableMatcher.start() + 1, variableMatcher.end()))
        }
        print("\u001b[0;37m")
        println("Done")
        return equationHead
    }
}