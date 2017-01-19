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

    public FixAxis(Axis fixAxisCoordinate, double value) {
        this.fixAxisCoordinate = fixAxisCoordinate;
        this.value = value;
    }

    @Override
    public double diff(Variable inputVar, Source inputSource) {
        //if(inputVar.getType() == VariableType.X) {
        //   return inputSource.get;
        return 0.0;
    }

    @Override
    public double doubleDiff(Variable diffVar1, Variable diffVar2, Source inputSource) {
        //if(inputVar.getType() == VariableType.X) {
        //   return inputSource.get;
        return 0.0;
    }
}
