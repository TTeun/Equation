package eqn.parser.exception;

public class BadSymbolException extends EqnException {

    final char _reservedSymbol;

    public BadSymbolException(char reservedSymbol) {
        super("Input contains a reserved symbol!");
        _reservedSymbol = reservedSymbol;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + _reservedSymbol + ')';
    }
}
