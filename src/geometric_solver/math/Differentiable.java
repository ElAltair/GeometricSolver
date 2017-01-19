package geometric_solver.math;

public interface Differentiable {

    double diff(Variable v, Source source);

    double doubleDiff(Variable v1, Variable v2, Source source);
}
