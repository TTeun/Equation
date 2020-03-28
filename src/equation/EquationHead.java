package equation;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class EquationHead {

    final public String functionName;
    final public Vector<String> variableNames;

    public EquationHead(String functionName) {
        this.functionName = functionName;
        this.variableNames = new Vector<String>();
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
