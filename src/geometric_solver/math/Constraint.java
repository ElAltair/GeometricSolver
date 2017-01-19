package geometric_solver.math;

import geometric_solver.geometry.Axis;

public abstract class Constraint implements Differentiable {

    abstract public double diff(Variable inputVar, Source inputSource);

    abstract public double doubleDiff(Variable diffVar1, Variable diffVar2, Source inputSource);


}
