package equation.parser;

import equation.EquationHead;
import equation.parser.exception.BadHeaderException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationHeadParser {

    static private final Pattern functionNamePattern = Pattern.compile("@[a-zA-Z]+\\(_0");
    static private final Pattern variableListPattern = Pattern.compile("(\\$[a-zA-Z]+,)*\\$[a-zA-Z]+");
    static private final Pattern variablePattern = Pattern.compile("\\$[a-zA-Z]+");

    static public EquationHead parseEquationHead(String equationHeadString) throws BadHeaderException {
        System.out.print("\033[1;37m");
        System.out.print("Parsing head: \t\t\t");

        equationHeadString = equationHeadString.trim();
        Matcher functionNameMatcher = functionNamePattern.matcher(equationHeadString);
        functionNameMatcher.find();

        if (functionNameMatcher.start() != 0) {
            throw new BadHeaderException("Start of header formatted incorrectly after pre-processing");
        }

        String functionName = functionNameMatcher.group().substring(1, functionNameMatcher.end() - 3);
        EquationHead equationHead = new EquationHead(functionName);

        String variableListString = equationHeadString.substring(functionNameMatcher.end());
        int endOfVariableListIndex = variableListString.length() - 4 - functionName.length();
        if (!variableListString.substring(endOfVariableListIndex).equals("@" + functionName + ")_0")) {
            throw new BadHeaderException("End of header formatted incorrectly after pre-processing");
        }

        variableListString = variableListString.substring(0, endOfVariableListIndex);
        Matcher variableListMatcher = variableListPattern.matcher(variableListString);
        if (!variableListMatcher.matches()) {
            throw new BadHeaderException("Formatting of arguments of function is incorrect");
        }

        Matcher variableMatcher = variablePattern.matcher(variableListString);

        while (variableMatcher.find()) {
            equationHead.addVariableName(variableListString.substring(variableMatcher.start() + 1, variableMatcher.end()));
        }

        System.out.print("\033[0;37m");
        System.out.println("Done");

        return equationHead;
    }


}
