package geometric_solver.math.constraints;

import geometric_solver.math.Constraint;
import geometric_solver.math.Source;
import geometric_solver.math.Variable;

/**
 * Created by Vladislav on 22/01/2017.
 */
public class FixAngle extends Constraint {

    private double fixedAngle;

    public FixAngle(double angle) {
        this.fixedAngle = angle;
    }

    @Override
    public double diff(Variable var, Source source) {
        return 0.0;
    }

    @Override
    public double doubleDiff(Variable varOne, Variable varTwo, Source source) {
        return 0.0;
    }

    @Override
    public double getValue() {
        return fixedAngle;
    }

    @Override
    public void setValue(double newConstVar) {

    }

    @Override
    public Variable getVariable() {
        return null;
    }

    @Override
    public double getStartVarValue() {
        return 0;
    }
}
