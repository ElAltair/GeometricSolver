package geometric_solver.math;

import geometric_solver.math.VariableType;

public class Variable {
    private static int XgenerateiD;
    private static int LgenerateiD;
    private int id;
    private VariableType type;

    public Variable(int id, VariableType type) {
        this.id = id;
        if (type == VariableType.X)
            XgenerateiD++;
        else if (type == VariableType.LAMBDA)
            LgenerateiD++;
        this.type = type;
    }

    public Variable(int id, double value, VariableType type) {
        this.id = id;
        this.type = type;
    }

    public static int generateID(VariableType type) {
        if (type == VariableType.X)
            return XgenerateiD;
        else
            return LgenerateiD;
    }

    public int getId() {
        return id;
    }

    public VariableType getType() {
        return type;
    }

    public void removeVariable(VariableType type) {
        if (type == VariableType.X)
            XgenerateiD--;
        else
            LgenerateiD--;
    }

    @Override
    public String toString() {
        return getType().name() + id;
    }

}
