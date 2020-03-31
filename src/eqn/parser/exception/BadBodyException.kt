package eqn.parser.exception

class BadBodyException : EqnException {
    constructor(exceptionMessage: String?) : super(exceptionMessage)
    constructor(nestedException: Exception?) : super("Bad equation body!", nestedException)
}