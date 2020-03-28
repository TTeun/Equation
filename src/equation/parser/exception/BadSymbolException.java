package equation.parser.exception;

public class BadSymbolException extends EquationException {

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
