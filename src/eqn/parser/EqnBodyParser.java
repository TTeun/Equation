package eqn.parser;

import eqn.ast.*;
import eqn.ast.predefined.*;
import eqn.parser.exception.BadBodyException;
import eqn.parser.exception.EqnException;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EqnBodyParser {

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
    private Vector<EqnAstNode> astNodes;

    private EqnBodyParser() {
        astNodes = new Vector<EqnAstNode>(0);
    }

    public static EqnAst parseEquationBody(String equationBodyString) throws EqnException {
//        System.out.println(equationBodyString);
        return new EqnAst((new EqnBodyParser()).parseEquationBody_internal(equationBodyString));
    }

    static private int findMaximumDepth(String equationBodyString, Pattern pattern) throws EqnException {
        int maximumBracketDepthSoFar = 0;
        Matcher bracketDepthMatcher = pattern.matcher(equationBodyString);
        while (bracketDepthMatcher.find()) {
            try {
                maximumBracketDepthSoFar = Math.max(maximumBracketDepthSoFar, Integer.parseInt(bracketDepthMatcher.group().substring(2)));
            } catch (Exception e) {
                throw new EqnException("Could not convert bracket depth identifier to int", new BadBodyException(e));
            }
        }
        return maximumBracketDepthSoFar;
    }

    private EqnAstNode parseEquationBody_internal(String equationBodyString) throws EqnException {
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


    private EqnAstNode parseEquationBody_NoBrackets_internal(String equationBodyString) throws EqnException {
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
                    astNodes.add(EqnAstNodePreDefinedUnaryFunction.create(functionName, parseEquationBody_NoBrackets_NoFunctions_internal(parameters[0])));
                } else {
                    astNodes.add(new EqnAstNodeCustomUnaryFunction(functionName, parseEquationBody_NoBrackets_NoFunctions_internal(parameters[0])));
                }
            } else {
                EqnAstNodeCustomFunction equationNode = new EqnAstNodeCustomFunction(functionName, parameters.length);
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

    private EqnAstNode parseEquationBody_NoBrackets_NoFunctions_internal(String equationBodyString) throws EqnException {
//        if (equationBodyString.charAt(0) == '-') {
//            return new EqnAstNodeUnaryOperation("-", parseEquationBody_NoBrackets_NoFunctions_internal(equationBodyString.substring(1)));
//        }
        {
            Matcher powerMatcher = powerPattern.matcher(equationBodyString);
            while (powerMatcher.find()) {
                String match = equationBodyString.substring(powerMatcher.start(), powerMatcher.end());
                String[] splitString = match.split("\\^");
                equationBodyString = equationBodyString.substring(0, powerMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(powerMatcher.end());
                astNodes.add(new EqnAstNodePower("^", stringToNode(splitString[0]), stringToNode(splitString[1])));
                powerMatcher.reset();
                powerMatcher = powerPattern.matcher(equationBodyString);
                ++replacementIndex;
            }
        }
        Matcher multMatcher = multiplyPattern.matcher(equationBodyString);
        while (multMatcher.find()) {
            String match = equationBodyString.substring(multMatcher.start(), multMatcher.end());
            String[] splitString = match.split("\\*|/");
            equationBodyString = equationBodyString.substring(0, multMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(multMatcher.end());
            if (match.contains("*")) {
                astNodes.add(new EqnAstNodeMultiply("*", stringToNode(splitString[0]), stringToNode(splitString[1])));
            } else {
                astNodes.add(new EqnAstNodeDivide("/", stringToNode(splitString[0]), stringToNode(splitString[1])));
            }

            ++replacementIndex;
            multMatcher.reset();
            multMatcher = multiplyPattern.matcher(equationBodyString);
        }

        Matcher unaryMinusMatcher = Pattern.compile("-" + valueString).matcher(equationBodyString);
        while (unaryMinusMatcher.lookingAt()) {
            String operand = equationBodyString.substring(unaryMinusMatcher.start() + 1, unaryMinusMatcher.end());
            System.out.println(operand);
            equationBodyString = equationBodyString.substring(0, unaryMinusMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(unaryMinusMatcher.end());
            astNodes.add(new EqnAstNodeUnaryOperation("-", stringToNode(operand)));
            unaryMinusMatcher.reset();
            unaryMinusMatcher = Pattern.compile("-" + valueString).matcher(equationBodyString);
            ++replacementIndex;
        }


        Matcher addMatcher = addPattern.matcher(equationBodyString);
        while (addMatcher.find()) {
            String match = equationBodyString.substring(addMatcher.start(), addMatcher.end());
            String[] splitString = match.split("\\+|-");
            equationBodyString = equationBodyString.substring(0, addMatcher.start()) + "<" + replacementIndex + equationBodyString.substring(addMatcher.end());

            if (match.contains("+")) {
                astNodes.add(new EqnAstNodeAdd("+", stringToNode(splitString[0]), stringToNode(splitString[1])));
            } else {
                astNodes.add(new EqnAstNodeSubtract("-", stringToNode(splitString[0]), stringToNode(splitString[1])));
            }
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

    private EqnAstNode stringToNode(String nodeString) throws BadBodyException {
        switch (nodeString.charAt(0)) {
            case '$':
                return new EqnAstNodeVariable(nodeString.substring(1));
            case '#':
                if (nodeString.contains(".")) {
                    return new EqnAstNodeDouble(nodeString.substring(1));
                }
                return new EqnAstNodeInteger(nodeString.substring(1));
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
