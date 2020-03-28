package eqn.parser;

import eqn.parser.exception.BadBracketsException;
import eqn.parser.exception.BadHeaderException;
import eqn.parser.exception.EqnException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EqnPreProcessor {

    static final Pattern functionCallPattern = Pattern.compile("(?<![@#])\\$[a-zA-Z]+\\s*\\(");
    static final Pattern variablePattern = Pattern.compile("(?<![@#])[a-zA-Z]+\\s*");
    static final Pattern numberPattern = Pattern.compile("([0-9]+\\.[0-9]+|[0-9]+)\\s*");

    static public String preProcessEquationString(@NotNull String equationString) throws EqnException {
        System.out.print("\033[1;37m");
        System.out.print("Pre-processing: \t\t");

        if (equationString.length() == 0) {
            throw new EqnException("Empty equation string");
        }
        equationString = equationString.trim();
        if (!Character.isAlphabetic(equationString.charAt(0))) {
            throw new BadHeaderException(new EqnException("No function name declared"));
        }
        if (!bracketsMatch(equationString)) {
            throw new BadBracketsException();
        }

        equationString = appendCharacterToAllMatches(equationString, variablePattern, '$', false);
        equationString = appendCharacterToAllMatches(equationString, functionCallPattern, '@', true);
        equationString = appendCharacterToAllMatches(equationString, numberPattern, '#', false);
        equationString = StringUtils.deleteWhitespace(equationString);
        equationString = replaceFunctionBrackets(equationString);
        equationString = addDepthToBrackets(equationString, 0, Pattern.compile("\\("), '(', ')', '[', ']');
        equationString = addDepthToBrackets(equationString, 0, Pattern.compile("\\{"), '{', '}', '(', ')');

        System.out.print("\033[0;37m");
        System.out.println("Done");

        return StringUtils.deleteWhitespace(equationString);
    }

    static private String appendCharacterToAllMatches(String equationString, Pattern pattern, char c, Boolean replaceLast) {
        assert (pattern != null);

        Matcher matcher = pattern.matcher(equationString);
        Vector<Integer> indices = new Vector<Integer>();
        while (matcher.find()) {
            indices.add(matcher.start());
        }
        if (indices.size() == 0) {
            return equationString;
        }
        indices.add(equationString.length());
        StringBuffer stringBuffer = new StringBuffer(equationString.substring(0, indices.get(0)));
        int indexOffSet = replaceLast ? 1 : 0;
        for (int i = 0; i != indices.size() - 1; ++i) {
            stringBuffer.append(c);
            stringBuffer.append(equationString, indices.get(i) + indexOffSet, indices.get(i + 1));
        }
        return new String(stringBuffer);
    }

    static private Boolean bracketsMatch(String equationString) {
        int bracketDepth = 0;
        CharacterIterator it = new StringCharacterIterator(equationString);
        for (char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
            if (ch == '(') {
                ++bracketDepth;
            } else if (ch == ')') {
                --bracketDepth;
                if (bracketDepth < 0) {
                    return false;
                }
            }
        }
        return bracketDepth == 0;
    }

    static private String addDepthToBrackets(String equationString, int depth, Pattern openBracketPattern, char bo, char bc, char newBo, char newBc) {
        Matcher matcher = openBracketPattern.matcher(equationString);
        while (matcher.find()) {
            int indexOfFunctionOpenBracket = matcher.end();
            int indexOfFunctionCloseBracket = -1;
            int bracketDepth = 0;
            for (int i = indexOfFunctionOpenBracket; i < equationString.length(); ++i) {
                if (equationString.charAt(i) == bo) {
                    ++bracketDepth;
                } else if (equationString.charAt(i) == bc) {
                    --bracketDepth;
                    if (bracketDepth == -1) {
                        indexOfFunctionCloseBracket = i;
                        break;
                    }
                }
            }
            assert (indexOfFunctionCloseBracket != -1);
            equationString = equationString.substring(0, indexOfFunctionOpenBracket - 1) + newBo + "_" + depth +
                    addDepthToBrackets(equationString.substring(matcher.end(), indexOfFunctionCloseBracket), depth + 1, openBracketPattern, bo, bc, newBo, newBc) +
                    newBc + "_" + depth + equationString.substring(indexOfFunctionCloseBracket + 1);

            matcher.reset();
            matcher = openBracketPattern.matcher(equationString);
        }

        return equationString;
    }

    static private String replaceFunctionBrackets(String equationString) {

        Pattern functionCallPatternWithAt = Pattern.compile("@[a-zA-Z]+\\(");
        Matcher matcher = functionCallPatternWithAt.matcher(equationString);
        if (matcher.find()) {
            int indexOfFunctionOpenBracket = matcher.end();
            int indexOfFunctionCloseBracket = -1;
            int bracketDepth = 0;
            for (int i = indexOfFunctionOpenBracket; i < equationString.length(); ++i) {
                if (equationString.charAt(i) == '(') {
                    ++bracketDepth;
                } else if (equationString.charAt(i) == ')') {
                    --bracketDepth;
                    if (bracketDepth == -1) {
                        indexOfFunctionCloseBracket = i;
                        break;
                    }
                }
            }
            assert (indexOfFunctionCloseBracket != -1);
            equationString = equationString.substring(0, indexOfFunctionOpenBracket - 1) + '{' + equationString.substring(matcher.end(), indexOfFunctionCloseBracket)
                    + matcher.group().substring(0, matcher.group().length() - 1) + '}' + equationString.substring(indexOfFunctionCloseBracket + 1);

            return replaceFunctionBrackets(equationString);
        }

        return equationString;
    }

}
