import eqn.Eqn;
import eqn.parser.EqnParser;
import eqn.parser.exception.EqnException;

public class Main {
    public static void main(String[] args) {
        try {
            Eqn equation = EqnParser.parseEquation("f(x,y) =12 +  2 * x * y *3 - z * 4 - 1 *4");
        } catch (EqnException e) {
            System.out.println(e.toString());
        }
    }
}
