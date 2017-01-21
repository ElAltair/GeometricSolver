package geometric_solver.math.constraints;

import geometric_solver.geometry.Axis;
import geometric_solver.math.Constraint;
import geometric_solver.math.Source;
import geometric_solver.math.Variable;
import geometric_solver.math.VariableType;

public class FixAxis extends Constraint {

    Axis fixAxisCoordinate;
    double value;
    Variable lambda;
    Variable x;

    public FixAxis(Axis fixAxisCoordinate, double value) {
        this.fixAxisCoordinate = fixAxisCoordinate;
        this.value = value;
        this.lambda = new Variable(Variable.generateID(VariableType.LAMBDA), VariableType.LAMBDA);
        this.x = new Variable(Variable.generateID(VariableType.X), VariableType.X);
    }


    @Override
    public double diff(Variable inputVar, Source inputSource) {
        if (x.equals(inputVar))
            return inputSource.getValue(x);
        else
            return 0.0;
    }

    @Override
    public double doubleDiff(Variable diffVar1, Variable diffVar2, Source inputSource) {
        if (x.equals(diffVar1)) {
            return diff(diffVar2, inputSource);
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "( " + x.getType().name() + " - " + value + ")" + " * " + lambda.getType().name();
    }

    @Override
    public  void setValue(double newConstVar){    }

    @Override
    public Variable getVariableType() {
        return x; };

    @Override
    public double getVariableValue() {return value;}
}
