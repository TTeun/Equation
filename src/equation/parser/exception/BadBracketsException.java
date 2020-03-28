package equation.parser.exception;

public class BadBracketsException extends EquationException {

    public BadBracketsException() {
        super("Brackets don't match!");
    }

}
