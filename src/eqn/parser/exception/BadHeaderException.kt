package eqn.parser.exception

class BadHeaderException : EqnException {
    constructor(equationException: EqnException?) : super("Bad header!", equationException)
    constructor(exceptionMessage: String?, equationException: EqnException?) : super(exceptionMessage, equationException)
    constructor(exceptionMessage: String?) : super(exceptionMessage)
}