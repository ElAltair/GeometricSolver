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

    public void add(Variable var, Double value) {
        variableList.put(var, value);
    }
}
