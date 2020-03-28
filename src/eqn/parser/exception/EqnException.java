package eqn.parser.exception;

public class EqnException extends Exception {

    Exception optionalNestedException = null;

    public EqnException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public EqnException(String exceptionMessage, Exception equationException) {
        super(exceptionMessage);
        this.optionalNestedException = equationException;
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(super.toString());

        if (optionalNestedException != null) {
            stringBuffer.append(" (");
            stringBuffer.append(optionalNestedException.toString());
            stringBuffer.append(')');

        }
        return new String(stringBuffer);
    }
}
