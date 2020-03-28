package eqn.parser;

import eqn.Eqn;
import eqn.parser.exception.BadBodyException;
import eqn.parser.exception.EqnException;
import org.apache.commons.lang3.StringUtils;

public class EqnParser {

    static public Eqn parseEquation(String equationString) throws EqnException {
        if (StringUtils.countMatches(equationString, '=') != 1) {
            throw new EqnException("Equation must contain exactly one '=' sign!");
        }

        equationString = EqnPreProcessor.preProcessEquationString(equationString);
        String[] splitString = equationString.split("=");
        if (splitString.length == 1) {
            throw new BadBodyException("Empty equation body");
        }

        return new Eqn(
                EqnHeadParser.parseEquationHead(splitString[0]),
                EqnBodyParser.parseEquationBody(splitString[1])
        );
    }


}
