package eqn.parser.exception;

public class BadHeaderException extends EqnException {

    public BadHeaderException(EqnException equationException) {
        super("Bad header!", equationException);
    }

    public BadHeaderException(String exceptionMessage, EqnException equationException) {
        super(exceptionMessage, equationException);
    }

    public BadHeaderException(String exceptionMessage) {
        super(exceptionMessage);
    }

}
