package equation.parser;

import equation.ast.*;
import equation.parser.exception.BadBodyException;
import equation.parser.exception.EquationException;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationBodyParser {

    static private final String numberString = "(#[0-9\\.]+)";
    static private final String variableString = "(\\$[a-zA-Z]+)";
    static private final String replacedString = "(\\<[0-9]+)";
    static private final String valueString = '(' + numberString + '|' + variableString + '|' + replacedString + ')';
    static private final Pattern bracketDepthPattern = Pattern.compile("\\[_[0-9]+");
    static private final Pattern FunctionDepthPattern = Pattern.compile("\\(_[0-9]+");
    static private final Pattern valuePattern = Pattern.compile(valueString);
    static private final Pattern powerPattern = Pattern.compile(valueString + "\\^" + valueString);
    static private final Pattern multiplyPattern = Pattern.compile(valueString + "(\\*|/)" + valueString);
    static private final Pattern addPattern = Pattern.compile(valueString + "(\\+|\\-)" + valueString);
    int replacementIndex = 0;
    private Vector<EquationAstNode> astNodes;

    private EquationBodyParser() {
        astNodes = new Vector<EquationAstNode>(0);
    }

    public static EquationAstNode parseEquationBody(String equationBodyString) throws EquationException {
        System.out.println(equationBodyString);
        EquationAstNode equationAstNode = (new EquationBodyParser()).parseEquationBody_internal(equationBodyString);
        return equationAstNode;
    }

    static private int findMaximumDepth(String equationBodyString, Pattern pattern) throws EquationException {
        int maximumBracketDepthSoFar = 0;
        Matcher bracketDepthMatcher = pattern.matcher(equationBodyString);
        while (bracketDepthMatcher.find()) {
            try {
                maximumBracketDepthSoFar = Math.max(maximumBracketDepthSoFar, Integer.parseInt(bracketDepthMatcher.group().substring(2)));
            } catch (Exception e) {
                throw new EquationException("Could not convert bracket depth identifier to int", new BadBodyException(e));
            }
        }
        return maximumBracketDepthSoFar;
    }

    private EquationAstNode parseEquationBody_internal(String equationBodyString) throws EquationException {
        while (equationBodyString.contains("[")) {
            int maximumBracketDepth = findMaximumDepth(equationBodyString, bracketDepthPattern);

            final Matcher endOfDeepestbracketMatcher = Pattern.compile("\\]_" + maximumBracketDepth).matcher(equationBodyString);
            if (!endOfDeepestbracketMatcher.find()) {
                throw new BadBodyException("Can't find end of deepest bracket!");
            }
            String shrunkEquationBody = equationBodyString.substring(0, endOfDeepestbracketMatcher.end());
            final Matcher startOfDeepestBracketMatcher = Pattern.compile("\\[_" + maximumBracketDepth).matcher(shrunkEquationBody);
            if (!startOfDeepestBracketMatcher.find()) {
                throw new BadBodyException("Can't find start of deepest bracket!");
            }
            String expressionInBrackets = equationBodyString.substring(startOfDeepestBracketMatcher.end(), endOfDeepestbracketMatcher.start());
            astNodes.add(parseEquationBody_NoBrackets_internal(expressionInBrackets));

            equationBodyString = equationBodyString.substring(0, startOfDeepestBracketMatcher.start()) + '<' + replacementIndex + equationBodyString.substring(endOfDeepestbracketMatcher.end());
            ++replacementIndex;
        }
        return parseEquationBody_NoBrackets_internal(equationBodyString);
    }


    private EquationAstNode parseEquationBody_NoBrackets_internal(String equationBodyString) throws EquationException {
        while (equationBodyString.contains("@")) {
            int maximumFunctionDepth = findMaximumDepth(equationBodyString, FunctionDepthPattern);

            final Matcher endOfDeepestFunctionMatcher = Pattern.compile("@[a-zA-Z]+\\)_" + maximumFunctionDepth).matcher(equationBodyString);
            if (!endOfDeepestFunctionMatcher.find()) {
                throw new BadBodyException("Can't find end of deepest function!");
            }
            String shrunkEquationBody = equationBodyString.substring(0, endOfDeepestFunctionMatcher.end());
            final Matcher startOfDeepestFunctionMatcher = Pattern.compile("@[a-zA-Z]+\\(_" + maximumFunctionDepth).matcher(shrunkEquationBody);
            if (!startOfDeepestFunctionMatcher.find()) {
                throw new BadBodyException("Can't find start of deepest function!");
            }
            shrunkEquationBody = equationBodyString.substring(startOfDeepestFunctionMatcher.start(), endOfDeepestFunctionMatcher.end());
            final Matcher functionNameMatcher = Pattern.compile("[a-zA-Z]+").matcher(shrunkEquationBody);
            if (!functionNameMatcher.find()) {
                throw new BadBodyException("Can't find function name!");
            }
            String functionName = functionNameMatcher.group();
            String[] parameters = equationBodyString.substring(startOfDeepestFunctionMatcher.end(), endOfDeepestFunctionMatcher.start()).split(",");

            if (parameters.length == 1) {
                if (Pattern.compile("sin|cos|tan|exp").matcher(functionName).matches()) {
                    astNodes.add(new EquationAstNodePreDefinedUnaryFunction(functionName, parseEquationBody_NoBrackets_NoFunctions_internal(parameters[0])));
                } else {
                    astNodes.add(new EquationAstNodeUnaryFunction(functionName, parseEquationBody_NoBrackets_NoFunctions_internal(parameters[0])));
                }
            } else {
                EquationAstNodeCustomFunction equationNode = new EquationAstNodeCustomFunction(functionName, parameters.length);
                for (int i = 0; i != parameters.length; ++i) {
                    equationNode.addOperand(parseEquationBody_NoBrackets_NoFunctions_internal(parameters[i]));
                }
                astNodes.add(equationNode);
            }
            equationBodyString = equationBodyString.substring(0, startOfDeepestFunctionMatcher.start()) + '<' + replacementIndex + equationBodyString.substring(endOfDeepestFunctionMatcher.end());
            ++replacementIndex;
        }
        return parseEquationBody_NoBrackets_NoFunctions_internal(equationBodyString);
    }

    private EquationAstNode parseEquationBody_NoBrackets_NoFunctions_internal(String equationBodyString) throws EquationException {
        if (equationBodyString.charAt(0) == '-') {
            return new EquationAstNodeUnaryOperation("-", parseEquationBody_NoBrackets_NoFunctions_internal(equationBodyString.substring(1)));
        }

        Matcher powerMatcher = powerPattern.matcher(equationBodyString);
        while (powerMatcher.find()) {
            String match = equationBodyString.substring(powerMatcher.start(), powerMatcher.end());
            String[] splitString = match.split("\\^");
            equationBodyString = equationBodyString.substring(0, powerMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(powerMatcher.end());
            astNodes.add(new EquationAstNodeBinaryOperation("^", stringToNode(splitString[0]), stringToNode(splitString[1])));
            powerMatcher.reset();
            powerMatcher = powerPattern.matcher(equationBodyString);
            ++replacementIndex;
        }

        Matcher multMatcher = multiplyPattern.matcher(equationBodyString);
        while (multMatcher.find()) {
            String match = equationBodyString.substring(multMatcher.start(), multMatcher.end());
            String operator;
            if (match.contains("*")) {
                operator = "*";
            } else {
                operator = "/";
            }

            String[] splitString = match.split("\\*|/");
            equationBodyString = equationBodyString.substring(0, multMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(multMatcher.end());
            astNodes.add(new EquationAstNodeBinaryOperation(operator, stringToNode(splitString[0]), stringToNode(splitString[1])));
            ++replacementIndex;
            multMatcher.reset();
            multMatcher = multiplyPattern.matcher(equationBodyString);
        }

        Matcher addMatcher = addPattern.matcher(equationBodyString);
        while (addMatcher.find()) {
            String match = equationBodyString.substring(addMatcher.start(), addMatcher.end());
            String operator;
            if (match.contains("+")) {
                operator = "+";
            } else {
                operator = "-";
            }
            String[] splitString = match.split("\\+|-");
            equationBodyString = equationBodyString.substring(0, addMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(addMatcher.end());
            astNodes.add(new EquationAstNodeBinaryOperation(operator, stringToNode(splitString[0]), stringToNode(splitString[1])));
            ++replacementIndex;
            addMatcher.reset();
            addMatcher = addPattern.matcher(equationBodyString);

        }

        Matcher valueMatcher = valuePattern.matcher(equationBodyString);
        if (valueMatcher.matches()) {
            return stringToNode(valueMatcher.group());
        } else {
            throw new BadBodyException("Couldn't resolve bracket-less and function-less expression");
        }

    }

    private EquationAstNode stringToNode(String nodeString) throws BadBodyException {
        switch (nodeString.charAt(0)) {
            case '$':
                return new EquationAstNodeVariable(nodeString.substring(1));
            case '#':
                if (nodeString.contains(".")) {
                    return new EquationAstNodeDouble(nodeString.substring(1));
                }
                return new EquationAstNodeInteger(nodeString.substring(1));
            case '<':
                int indexOfNode = Integer.parseInt(nodeString.substring(1));
                if (astNodes.elementAt(indexOfNode) == null) {
                    throw new BadBodyException("astNodes at index is null, shouldn't happen!");
                }
                return astNodes.elementAt(indexOfNode);

        }
        throw new BadBodyException("Unexpected symbol");
    }
}
