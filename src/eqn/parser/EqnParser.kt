package eqn.parser

import eqn.Eqn
import eqn.parser.exception.BadBodyException
import eqn.parser.exception.EqnException
import org.apache.commons.lang3.StringUtils

object EqnParser {
    @JvmStatic
    @Throws(EqnException::class)
    fun parseEquation(equationString: String?): Eqn {
        var equationString = equationString
        if (StringUtils.countMatches(equationString, '=') != 1) {
            throw EqnException("Equation must contain exactly one '=' sign!")
        }
        equationString = EqnPreProcessor.preProcessEquationString(equationString!!)
        val splitString = equationString.split("=".toRegex()).toTypedArray()
        if (splitString.size == 1) {
            throw BadBodyException("Empty equation body")
        }
        return Eqn(
                EqnHeadParser.parseEquationHead(splitString[0]),
                EqnBodyParser.parseEquationBody(splitString[1])
        )
    }
}