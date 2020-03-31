package eqn

class EqnHead(private val functionName: String, private val variableNames: ArrayList<String> = ArrayList()) {
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