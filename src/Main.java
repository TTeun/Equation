import equation.Equation;
import equation.parser.EquationParser;
import equation.parser.exception.EquationException;

public class Main {
    public static void main(String[] args) {
        try {
            Equation equation = EquationParser.parseEquation("f(x,y)= 1+1+1+1 + b + sin(9)");
        } catch (EquationException e) {
            System.out.println(e.toString());
        }
    }
}
