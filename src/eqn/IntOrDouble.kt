package eqn

class IntOrDouble {
    var double: Double
    var int: Int
    var isInteger: Boolean = true

    constructor(int: Int) {
        double = Double.NaN
        this.int = int
    }

    constructor(double: Double) {
        this.double = double
        this.int = Int.MIN_VALUE
        isInteger = false
    }

    override fun toString(): String {
        return if (isInteger) "$int" else "$double"
    }

    fun plus(a: Int) {
        if (isInteger) {
            int += a
        } else {
            double += a
        }
    }

    fun plus(a: Double) {
        if (isInteger) {
            double = int.toDouble()
            isInteger = false
        }
        double += a
    }


}