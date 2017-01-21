package geometric_solver.math.constraints;

import geometric_solver.math.Constraint;
import geometric_solver.math.Source;
import geometric_solver.math.Variable;
import geometric_solver.math.VariableType;

public class FixLength extends Constraint {

    @Override
    public double diff(Variable var, Source source) {
        return 0.0;
    }

    @Override
    public double doubleDiff(Variable varOne, Variable varTwo, Source source) {
        return 0.0;
    }

    @Override
    public Variable getVariable() {
        return new Variable(1, VariableType.X);
    }

    @Override
    public double getValue() {
        return 0.0;
    }

    @Override
    public void setValue(double newConstVar) {
    }

    @Override
    public double getStartVarValue() {
        return 0.0;
    }
}
