package equation.parser.exception;

public class BadHeaderException extends EquationException {

    public BadHeaderException(EquationException equationException) {
        super("Bad header!", equationException);
    }

    public BadHeaderException(String exceptionMessage, EquationException equationException) {
        super(exceptionMessage, equationException);
    }

    public BadHeaderException(String exceptionMessage) {
        super(exceptionMessage);
    }

}
