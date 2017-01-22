package geometric_solver.math.constraints;

import geometric_solver.geometry.Line;
import geometric_solver.math.Constraint;
import geometric_solver.math.Source;
import geometric_solver.math.Variable;
import geometric_solver.math.VariableType;

/**
 * Created by Vladislav on 22/01/2017.
 */
public class FixParallel extends Constraint {

    private Line firstLine;
    private Line secondLine;
    private Variable lambda;

    public FixParallel(Line l1, Line l2) {
        this.firstLine = l1;
        this.secondLine = l2;
        this.lambda = new Variable(Variable.generateID(VariableType.LAMBDA), VariableType.LAMBDA);
    }

    @Override
    public double diff(Variable var, Source source) {
        // X1
        Variable x1 = firstLine.getP1().getSquaredSummX().getVariable();
        // X2
        Variable x2 = firstLine.getP2().getSquaredSummX().getVariable();
        // X3
        Variable x3 = secondLine.getP1().getSquaredSummX().getVariable();
        // X4
        Variable x4 = secondLine.getP2().getSquaredSummX().getVariable();
        // Y1
        Variable y1 = firstLine.getP1().getSquaredSummY().getVariable();
        // Y2
        Variable y2 = firstLine.getP2().getSquaredSummY().getVariable();
        // Y3
        Variable y3 = secondLine.getP1().getSquaredSummY().getVariable();
        // Y4
        Variable y4 = secondLine.getP2().getSquaredSummY().getVariable();
        if (x1.equals(var)) {
            return -source.getValue(y4) * source.getValue(lambda) + source.getValue(y3) * source.getValue(lambda);
        } else if (x2.equals(var)) {
            return -source.getValue(y4) * source.getValue(lambda) + source.getValue(y3) * source.getValue(lambda);
        } else if (x3.equals(var)) {
            return source.getValue(y2) * source.getValue(lambda) - source.getValue(y1) * source.getValue(lambda);
        } else if (x4.equals(var)) {
            return -source.getValue(y2) * source.getValue(lambda) + source.getValue(y1) * source.getValue(lambda);
        } else if (y1.equals(var)) {
            return source.getValue(x4) * source.getValue(lambda) - source.getValue(x3) * source.getValue(lambda);
        } else if (y2.equals(var)) {
            return -source.getValue(x4) * source.getValue(lambda) + source.getValue(x3) * source.getValue(lambda);
        } else if (y3.equals(var)) {
            return source.getValue(x1) * source.getValue(lambda) - source.getValue(x2) * source.getValue(lambda);
        } else if (y4.equals(var)) {
            return source.getValue(x2) * source.getValue(lambda) - source.getValue(x1) * source.getValue(lambda);
        } else if (lambda.equals(var)) {
            return source.getValue(x2) * source.getValue(y4)
                    - source.getValue(x1) * source.getValue(y4)
                    - source.getValue(x2) * source.getValue(y3)
                    + source.getValue(x1) * source.getValue(y3)
                    - source.getValue(y2) * source.getValue(x4)
                    + source.getValue(y1) * source.getValue(x4)
                    + source.getValue(y2) * source.getValue(x3)
                    - source.getValue(y1) * source.getValue(x3);

        } else
            return 0.0;
    }

    @Override
    public double doubleDiff(Variable varOne, Variable varTwo, Source source) {
        return 0.0;
    }

    @Override
    public double getValue() {
        return 0.0;
    }

    @Override
    public void setValue(double newConstVar) {

    }

    @Override
    public Variable getVariable() {
        return null;
    }

    @Override
    public double getStartVarValue() {
        return 0;
    }
}
