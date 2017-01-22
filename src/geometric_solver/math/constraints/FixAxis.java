package geometric_solver.math.constraints;

import geometric_solver.geometry.Axis;
import geometric_solver.math.Constraint;
import geometric_solver.math.Source;
import geometric_solver.math.Variable;
import geometric_solver.math.VariableType;

public class FixAxis extends Constraint {

    private Axis fixAxisCoordinate;
    private double value;
    private Variable lambda;
    private Variable var;

    public FixAxis(Axis fixAxisCoordinate, double value, Variable var) {
        this.fixAxisCoordinate = fixAxisCoordinate;
        this.value = value;
        this.var = var;
        this.lambda = new Variable(Variable.generateID(VariableType.LAMBDA), VariableType.LAMBDA);
    }


    @Override
    public double diff(Variable inputVar, Source inputSource) {
        // diff X in formula
        if (var.equals(inputVar))
            return inputSource.getValue(lambda);
            // diff Lambda in formula
        else if (lambda.equals(inputVar))
            return inputSource.getValue(var) - value;
        else
            return 0.0;
    }

    @Override
    public double doubleDiff(Variable diffVar1, Variable diffVar2, Source inputSource) {
        if (var.equals(diffVar1) && lambda.equals(diffVar2)) {
            return 1.0;
        } else if (lambda.equals(diffVar1) && var.equals(diffVar2))
            return 1.0;
        else
            return 0.0;
    }

    @Override
    public String toString() {
        return lambda.getType().name() + lambda.getId() + " * ( " + var.getType().name() + var.getId() + " - " + value + ")";
    }

    @Override
    public Variable getVariable() {
        return lambda;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double newConstVar) {
        this.value = newConstVar;
    }

    public Axis getAxis() {
        return fixAxisCoordinate;
    }

    @Override
    public double getStartVarValue() {
        return 0.0;
    }
}
