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
    public double diff(Variable var, Source source) {
        if (this.var.equals(var))
            return 2 * source.getValue(var) + 2 * constVar;
        else
            return 0.0;
    }
}
