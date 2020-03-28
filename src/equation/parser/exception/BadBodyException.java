package equation.parser.exception;

public class BadBodyException extends EquationException {

    public BadBodyException() {
        super("Bad equation body!");
    }

    public BadBodyException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public BadBodyException(Exception nestedException) {
        super("Bad equation body!", nestedException);
    }

}
