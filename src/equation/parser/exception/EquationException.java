package equation.parser.exception;

public class EquationException extends Exception {

    Exception optionalNestedException = null;

    public EquationException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public EquationException(String exceptionMessage, Exception equationException) {
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
