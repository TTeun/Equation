import eqn.parser.EqnParser.parseEquation
import eqn.parser.exception.EqnException

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val equation = parseEquation("f(x,y) = x ^ y")
            println(equation.evaluate(mapOf("x" to 2.0, "y" to 1.1, "z" to 9.0)))
            equation.simplify(mapOf("x" to 2.0, "y" to 1.1))
            println("$equation")
        } catch (e: EqnException) {
            println(e.toString())
        }
    }
}