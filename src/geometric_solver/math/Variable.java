package geometric_solver.math;

import geometric_solver.math.VariableType;

public class Variable {
    private int id;
    private VariableType type;

    public Variable(int id, VariableType type) {
        this.id = id;
        this.type = type;
    }

    public Variable(int id, double value, VariableType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public VariableType getType() {
        return type;
    }

}
