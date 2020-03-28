package eqn.parser.exception;

public class BadBracketsException extends EqnException {

    public BadBracketsException() {
        super("Brackets don't match!");
    }

}
