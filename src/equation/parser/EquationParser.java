package equation.parser;

import equation.Equation;
import equation.parser.exception.BadBodyException;
import equation.parser.exception.EquationException;
import org.apache.commons.lang3.StringUtils;

public class EquationParser {

    static public Equation parseEquation(String equationString) throws EquationException {
        if (StringUtils.countMatches(equationString, '=') != 1) {
            throw new EquationException("Equation must contain exactly one '=' sign!");
        }

        equationString = EquationPreProcessor.preProcessEquationString(equationString);
        String[] splitString = equationString.split("=");
        if (splitString.length == 1) {
            throw new BadBodyException("Empty equation body");
        }

        return new Equation(
                EquationHeadParser.parseEquationHead(splitString[0]),
                EquationBodyParser.parseEquationBody(splitString[1])
        );
    }


}
