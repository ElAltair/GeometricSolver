package geometric_solver.math;

import java.util.ArrayList;

public class Lagrange {
    private ArrayList<Differentiable> functonParts;
    private SquaredSumm firstSumm;
    private SquaredSumm secondSumm;

    public Lagrange(SquaredSumm first, SquaredSumm second) {
        functonParts = new ArrayList<>();
        firstSumm = first;
        secondSumm = second;
    }

    public double diff(Variable var, Source source) {
        return firstSumm.diff(var, source) + secondSumm.diff(var, source) +
                functonParts.stream().mapToDouble((part) -> part.diff(var, source)).sum();
    }
}
