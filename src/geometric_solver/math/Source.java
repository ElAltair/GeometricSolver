package geometric_solver.math;

import java.util.HashMap;
import java.util.Map;

public class Source {
    private Map<Variable, Double> variableList;

    public Source() {
        variableList = new HashMap<>();
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
        for (int i = 0; i < variableList.size(); ++i) {
            variableList.put(getVariable(i), vector[i]);
        }
    }
}
