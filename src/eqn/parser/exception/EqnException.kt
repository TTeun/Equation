package eqn.parser.exception

open class EqnException : Exception {
    var optionalNestedException: Exception? = null

    constructor(exceptionMessage: String?) : super(exceptionMessage) {}
    constructor(exceptionMessage: String?, equationException: Exception?) : super(exceptionMessage) {
        optionalNestedException = equationException
    }

    override fun toString(): String {
        val stringBuffer = StringBuffer(super.toString())
        if (optionalNestedException != null) {
            stringBuffer.append(" (")
            stringBuffer.append(optionalNestedException.toString())
            stringBuffer.append(')')
        }
        return String(stringBuffer)
    }
}