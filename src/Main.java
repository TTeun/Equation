import eqn.Eqn;
import eqn.parser.EqnParser;
import eqn.parser.exception.EqnException;

public class Main {
    public static void main(String[] args) {
        try {
            Eqn equation = EqnParser.parseEquation("f(x,y)= sin(9)");
            System.out.println(equation.evaluate());
        } catch (EqnException e) {
            System.out.println(e.toString());
        }
    }
}
