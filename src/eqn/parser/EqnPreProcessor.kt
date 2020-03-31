package eqn.parser

import eqn.parser.exception.BadBracketsException
import eqn.parser.exception.BadHeaderException
import eqn.parser.exception.EqnException
import org.apache.commons.lang3.StringUtils
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import java.util.*
import java.util.regex.Pattern

object EqnPreProcessor {
    private val functionCallPattern: Pattern = Pattern.compile("(?<![@#])\\$[a-zA-Z]+\\s*\\(")
    private val variablePattern: Pattern = Pattern.compile("(?<![@#])[a-zA-Z]+\\s*")
    private val numberPattern: Pattern = Pattern.compile("([0-9]+\\.[0-9]+|[0-9]+)\\s*")

    @Throws(EqnException::class)
    fun preProcessEquationString(equationString: String): String {
        var equationString = equationString
        print("\u001b[1;37m")
        print("Pre-processing: \t\t")
        if (equationString.isEmpty()) {
            throw EqnException("Empty equation string")
        }
        equationString = equationString.trim { it <= ' ' }
        if (!Character.isAlphabetic(equationString[0].toInt())) {
            throw BadHeaderException(EqnException("No function name declared"))
        }
        if (!bracketsMatch(equationString)) {
            throw BadBracketsException()
        }
        equationString = appendCharacterToAllMatches(equationString, variablePattern, '$', false)
        equationString = appendCharacterToAllMatches(equationString, functionCallPattern, '@', true)
        equationString = appendCharacterToAllMatches(equationString, numberPattern, '#', false)
        equationString = StringUtils.deleteWhitespace(equationString)
        equationString = replaceFunctionBrackets(equationString)
        equationString = addDepthToBrackets(equationString, 0, Pattern.compile("\\("), '(', ')', '[', ']')
        equationString = addDepthToBrackets(equationString, 0, Pattern.compile("\\{"), '{', '}', '(', ')')
        print("\u001b[0;37m")
        println("Done")
        return StringUtils.deleteWhitespace(equationString)
    }

    private fun appendCharacterToAllMatches(equationString: String, pattern: Pattern?, c: Char, replaceLast: Boolean): String {
        assert(pattern != null)
        val matcher = pattern!!.matcher(equationString)
        val indices = Vector<Int>()
        while (matcher.find()) {
            indices.add(matcher.start())
        }
        if (indices.size == 0) {
            return equationString
        }
        indices.add(equationString.length)
        val stringBuffer = StringBuffer(equationString.substring(0, indices[0]))
        val indexOffSet = if (replaceLast) 1 else 0
        for (i in 0 until indices.size - 1) {
            stringBuffer.append(c)
            stringBuffer.append(equationString, indices[i] + indexOffSet, indices[i + 1])
        }
        return String(stringBuffer)
    }

    private fun bracketsMatch(equationString: String): Boolean {
        var bracketDepth = 0
        val it: CharacterIterator = StringCharacterIterator(equationString)
        var ch = it.first()
        while (ch != CharacterIterator.DONE) {
            if (ch == '(') {
                ++bracketDepth
            } else if (ch == ')') {
                --bracketDepth
                if (bracketDepth < 0) {
                    return false
                }
            }
            ch = it.next()
        }
        return bracketDepth == 0
    }

    private fun addDepthToBrackets(equationString: String, depth: Int, openBracketPattern: Pattern, bo: Char, bc: Char, newBo: Char, newBc: Char): String {
        var equationString = equationString
        var matcher = openBracketPattern.matcher(equationString)
        while (matcher.find()) {
            val indexOfFunctionOpenBracket = matcher.end()
            var indexOfFunctionCloseBracket = -1
            var bracketDepth = 0
            for (i in indexOfFunctionOpenBracket until equationString.length) {
                if (equationString[i] == bo) {
                    ++bracketDepth
                } else if (equationString[i] == bc) {
                    --bracketDepth
                    if (bracketDepth == -1) {
                        indexOfFunctionCloseBracket = i
                        break
                    }
                }
            }
            assert(indexOfFunctionCloseBracket != -1)
            equationString = equationString.substring(0, indexOfFunctionOpenBracket - 1) + newBo + "_" + depth +
                    addDepthToBrackets(equationString.substring(matcher.end(), indexOfFunctionCloseBracket), depth + 1, openBracketPattern, bo, bc, newBo, newBc) +
                    newBc + "_" + depth + equationString.substring(indexOfFunctionCloseBracket + 1)
            matcher.reset()
            matcher = openBracketPattern.matcher(equationString)
        }
        return equationString
    }

    private fun replaceFunctionBrackets(equationString: String): String {
        var equationString = equationString
        val functionCallPatternWithAt = Pattern.compile("@[a-zA-Z]+\\(")
        val matcher = functionCallPatternWithAt.matcher(equationString)
        if (matcher.find()) {
            val indexOfFunctionOpenBracket = matcher.end()
            var indexOfFunctionCloseBracket = -1
            var bracketDepth = 0
            for (i in indexOfFunctionOpenBracket until equationString.length) {
                if (equationString[i] == '(') {
                    ++bracketDepth
                } else if (equationString[i] == ')') {
                    --bracketDepth
                    if (bracketDepth == -1) {
                        indexOfFunctionCloseBracket = i
                        break
                    }
                }
            }
            assert(indexOfFunctionCloseBracket != -1)
            equationString = (equationString.substring(0, indexOfFunctionOpenBracket - 1) + '{' + equationString.substring(matcher.end(), indexOfFunctionCloseBracket)
                    + matcher.group().substring(0, matcher.group().length - 1) + '}' + equationString.substring(indexOfFunctionCloseBracket + 1))
            return replaceFunctionBrackets(equationString)
        }
        return equationString
    }
}