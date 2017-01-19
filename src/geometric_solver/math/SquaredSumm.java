package geometric_solver.math;

import geometric_solver.math.Differentiable;
import geometric_solver.math.Source;
import geometric_solver.math.Variable;

public class SquaredSumm implements Differentiable {

    private Variable var;
    private double constVar;

    public SquaredSumm(Variable var, double constVar) {
        this.var = var;
        this.constVar = constVar;
    }

    @Override
    public double diff(Variable inputVar, Source inputSource) {
        if (var.equals(inputVar))
            return 2 * inputSource.getValue(var) + 2 * constVar;
        else
            return 0.0;
    }

    @Override
    public double doubleDiff(Variable diffVar1, Variable diffVar2, Source inputSource) {
        if (var.equals(diffVar1)) {
            return diff(diffVar2, inputSource);
        }
        return 0.0;
    }
}
