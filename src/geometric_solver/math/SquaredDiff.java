package geometric_solver.math;

public class SquaredDiff implements Differentiable {

    private Variable var;
    private double constVar;
    private double startValue;

    private SquaredDiff(Variable var, double constVar, double startValue) {
        this.var = var;
        this.constVar = constVar;
        this.startValue = startValue;
    }

    public static SquaredDiff build(double constVar, double startValue) {
        return new SquaredDiff(new Variable(Variable.generateID(VariableType.X), VariableType.X), constVar, startValue);
    }

    @Override
    public double diff(Variable inputVar, Source inputSource) {
        if (var.equals(inputVar))
            return 2 * inputSource.getValue(var) - 2 * constVar;
        else
            return 0.0;
    }

    @Override
    public double doubleDiff(Variable diffVar1, Variable diffVar2, Source inputSource) {
        if (var.equals(diffVar1) && var.equals(diffVar2)) {
            return 2.0;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "( " + var.getType().name() + var.getId() + " - " + constVar + ") ^ 2";
    }

    @Override
    public double getValue() {
        return constVar;
    }

    @Override
    public void setValue(double newConstVar) {
        this.constVar = newConstVar;
    }

    @Override
    public Variable getVariable() {
        return var;
    }

    @Override
    public double getStartVarValue() {
        return startValue;
    }
}
