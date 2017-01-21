package geometric_solver.math;

public interface Differentiable {

    double diff(Variable v, Source source);

    double doubleDiff(Variable v1, Variable v2, Source source);

    //Это костыль, чтобы можно было везде заментить SqueredSumm на Differentiable
    void setValue(double newConstVar);

    // Пишу для проверки без UI. Потом можно будет удалить, скорее всего
    Variable getVariableType();

    double getVariableValue();
}
