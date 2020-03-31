package eqn;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class EqnHead {

    final public String functionName;
    final public Vector<String> variableNames;

    public EqnHead(String functionName) {
        this.functionName = functionName;
        this.variableNames = new Vector<>();
    }

    public void addVariableName(@NotNull String name) {
        this.variableNames.add(name);
    }

    @Override
    public String toString() {
        return "EquationHead{" +
                "functionName='" + functionName + '\'' +
                ", variableNames=" + variableNames +
                '}';
    }
}
