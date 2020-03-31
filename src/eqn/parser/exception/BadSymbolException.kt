package eqn.parser.exception

class BadSymbolException(val _reservedSymbol: Char) : EqnException("Input contains a reserved symbol!") {
    override fun toString(): String {
        return super.toString() + " (" + _reservedSymbol + ')'
    }

}