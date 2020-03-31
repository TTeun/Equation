package eqn

import java.util.*

class EqnHead(private val functionName: String) {
    private val variableNames: Vector<String> = Vector()
    fun addVariableName(name: String) {
        variableNames.add(name)
    }

    override fun toString(): String {
        return "EquationHead{" +
                "functionName='" + functionName + '\'' +
                ", variableNames=" + variableNames +
                '}'
    }

}