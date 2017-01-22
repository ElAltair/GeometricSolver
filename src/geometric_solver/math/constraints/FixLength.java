package geometric_solver.math.constraints;

import geometric_solver.math.Constraint;
import geometric_solver.math.Source;
import geometric_solver.math.Variable;
import geometric_solver.math.VariableType;

public class FixLength extends Constraint {
    private double fixLength;
    private Variable xStart;
    private Variable yStart;
    private Variable xEnd;
    private Variable yEnd;
    private Variable lambda;

    public FixLength(Variable xStart, Variable yStart, Variable xEnd, Variable yEnd, double fixLength) {
        this.fixLength = fixLength;
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
        this.lambda = new Variable(Variable.generateID(VariableType.LAMBDA), VariableType.LAMBDA);
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

    @Override
    public String toString() {
        return lambda.getType().name() + lambda.getId() + " * ( ( " + xEnd.getType().name() + xEnd.getId() +
                " - " + xStart.getType().name() + xEnd.getId() + ") ^ 2 + ( " + yEnd.getType().name() + yEnd.getId() +
                " - " + yStart.getType().name() + yStart.getId() + " ) ^ 2  - " + fixLength + " ^ 2 )";
    }
}
