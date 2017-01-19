package geometric_solver.math;

import java.util.ArrayList;

public class Lagrange {
    private ArrayList<Differentiable> functonParts;
    private Source source;

    public Lagrange(Source source) {
        functonParts = new ArrayList<>();
        this.source = source;
    }

    public void addComponents(ArrayList<SquaredSumm> pointComponenst) {
        for (Differentiable it : pointComponenst) {
            functonParts.add(it);
        }
    }

    public void changePosition(double pointValueX, double pointValueY) {
        Variable pointVarX = new Variable(Variable.generateID(VariableType.X), VariableType.X);
        SquaredSumm squaredX = new SquaredSumm(pointVarX, pointValueX);
        source.add(pointVarX, pointValueX);
        functonParts.add(squaredX);

        Variable pointVarY = new Variable(Variable.generateID(VariableType.X), VariableType.X);
        SquaredSumm squaredY = new SquaredSumm(pointVarY, pointValueY);
        source.add(pointVarY, pointValueY);
        functonParts.add(squaredY);
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

    public String print() {
        String fullString = "";
        for (Differentiable it : functonParts) {
            fullString += it.toString() + "+";
        }
        return fullString;
    }
}
