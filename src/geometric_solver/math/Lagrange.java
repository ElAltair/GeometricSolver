package geometric_solver.math;

import java.util.ArrayList;

public class Lagrange {
    private ArrayList<Differentiable> functonParts;
    private Source source;

    public Lagrange(Source source) {
        functonParts = new ArrayList<>();
        this.source = source;
    }

    public Lagrange addPoint(double pointValue) {
        Variable pointVar = new Variable(Variable.generateID(VariableType.X), VariableType.X);
        SquaredSumm squared = new SquaredSumm(pointVar, pointValue);
        source.add(pointVar, pointValue);
        functonParts.add(squared);
        return this;
    }

    public Lagrange addConstraint(Constraint constraint) {
        functonParts.add(constraint);
        return this;
    }

    public double diff(Variable var, Source source) {
        return functonParts.stream().mapToDouble((part) -> part.diff(var, source)).sum();
    }

    public double doubleDiff(Variable diffVar1, Variable diffVar2, Source source) {
        return functonParts.stream().mapToDouble((part) -> part.doubleDiff(diffVar1, diffVar2, source)).sum();
    }
}
