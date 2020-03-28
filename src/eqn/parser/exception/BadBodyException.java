package eqn.parser.exception;

public class BadBodyException extends EqnException {

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
