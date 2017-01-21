package geometric_solver.math;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Source {
    private LinkedHashMap<Variable, Double> variableList;

    public Source() {
        variableList = new LinkedHashMap<>();
    }

    public Double getValue(Variable var) {
        return variableList.get(var);
    }

    public Double getValue(int varID) {
        for (Variable var : variableList.keySet())
            if (var.getId() == varID)
                return variableList.get(var);
        return -1.0;
    }

    public void setVariable(Variable var, double value) {
        variableList.put(var, value);
    }

    public Variable getVariable(int varID, VariableType type) {
        for (Variable it : variableList.keySet())
            if (it.getId() == varID && it.getType() == type)
                return it;
        return null;
    }

    public Variable getVariable(int varID) {
        for (Variable it : variableList.keySet())
            if (it.getId() == varID)
                return it;
        return null;
    }

    public Source add(Variable var, Double value) {
        variableList.put(var, value);
        return this;
    }

    public int getSize() {
        int size = 0;
        for (Variable it : variableList.keySet()) {
            size++;
        }
        return size;
    }

    public void update(double[] vector) {
        int i = 0;
        for (Variable it : variableList.keySet()) {
            variableList.put(it, variableList.get(it) + vector[i]);
            ++i;
        }
    }

    public LinkedHashMap<Variable, Double> getVariables() {
        return variableList;
    }

    public void print() {
        System.out.println("Source: ");
        for (Variable it : variableList.keySet()) {
            System.out.println(it.getType().name() + it.getId() + " " + variableList.get(it));
        }
    }
}
