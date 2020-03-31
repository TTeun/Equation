package eqn.parser.exception

class BadSymbolException(private val reservedSymbol: Char) : EqnException("Input contains a reserved symbol!") {
    override fun toString(): String {
        return super.toString() + " (" + reservedSymbol + ')'
    }

}