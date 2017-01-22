package geometric_solver.math.constraints;

import geometric_solver.math.Constraint;
import geometric_solver.math.Source;
import geometric_solver.math.Variable;
import geometric_solver.math.VariableType;

public class FixLength extends Constraint {
    private double fixLength;
    private Variable xStart;
    private Variable yStart;
    private Variable xEnd;
    private Variable yEnd;
    private Variable lambda;

    public FixLength(Variable xStart, Variable yStart, Variable xEnd, Variable yEnd, double fixLength) {
        if(fixLength == 0)
            fixLength = 0.1;
        this.fixLength = fixLength;
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
        this.lambda = new Variable(Variable.generateID(VariableType.LAMBDA), VariableType.LAMBDA);
    }

    @Override
    public double diff(Variable var, Source source) {
        if (xStart.equals(var)) {
            return -2 * source.getValue(lambda) * source.getValue(xEnd) +
                    2 * source.getValue(xStart) * source.getValue(lambda);
        } else if (xEnd.equals(var)) {
            return 2 * source.getValue(xEnd) * source.getValue(lambda) -
                    2 * source.getValue(lambda) * source.getValue(xStart);
        } else if (yStart.equals(var)) {
            return -2 * source.getValue(lambda) * source.getValue(yEnd) +
                    2 * source.getValue(yStart) * source.getValue(lambda);
        } else if (yEnd.equals(var)) {
            return 2 * source.getValue(yEnd) * source.getValue(lambda) -
                    2 * source.getValue(lambda) * source.getValue(yStart);
        } else if (lambda.equals(var)) {
            return source.getValue(xEnd) * source.getValue(xEnd) - 2 * source.getValue(xStart) * source.getValue(xEnd) +
                    source.getValue(xStart) * source.getValue(xStart) + source.getValue(yEnd) * source.getValue(yEnd) -
                    2 * source.getValue(yStart) * source.getValue(yEnd) + source.getValue(yStart) * source.getValue(yStart) -
                    fixLength * fixLength;
        } else
            return 0.0;
    }

    @Override
    public double doubleDiff(Variable varOne, Variable varTwo, Source source) {
        if (xStart.equals(varOne)) {
            if (xStart.equals(varTwo)) {
                return 2 * source.getValue(lambda);
            } else if (xEnd.equals(varTwo)) {
                return -2 * source.getValue(lambda);
            } else if (lambda.equals(varTwo)) {
                return -2 * source.getValue(xEnd) + 2 * source.getValue(xStart);
            } else return 0.0;
        } else if (xEnd.equals(varOne)) {
            if (xStart.equals(varTwo)) {
                return -2 * source.getValue(lambda);
            } else if (xEnd.equals(varTwo)) {
                return 2 * source.getValue(lambda);
            } else if (lambda.equals(varTwo)) {
                return 2 * source.getValue(xEnd) - 2 * source.getValue(xStart);
            } else return 0.0;
        } else if (yStart.equals(varOne)) {
            if (yStart.equals(varTwo)) {
                return 2 * source.getValue(lambda);
            } else if (yEnd.equals(varTwo)) {
                return -2 * source.getValue(lambda);
            } else if (lambda.equals(varTwo)) {
                return -2 * source.getValue(yEnd) + 2 * source.getValue(yStart);
            } else
                return 0.0;
        } else if (yEnd.equals(varOne)) {
            if (yStart.equals(varTwo)) {
                return -2 * source.getValue(lambda);
            } else if (yEnd.equals(varTwo)) {
                return 2 * source.getValue(lambda);
            } else if (lambda.equals(varTwo)) {
                return 2 * source.getValue(yEnd) - 2 * source.getValue(yStart);
            } else return 0.0;
        } else if (lambda.equals(varOne)) {
            if (xStart.equals(varTwo)) {
                return -2 * source.getValue(xEnd) + 2 * source.getValue(xStart);
            } else if (xEnd.equals(varTwo)) {
                return 2 * source.getValue(xEnd) - 2 * source.getValue(xStart);
            } else if (yStart.equals(varTwo)) {
                return -2 * source.getValue(yEnd) + 2 * source.getValue(yStart);
            } else if (yEnd.equals(varTwo)) {
                return 2 * source.getValue(yEnd) - 2 * source.getValue(yStart);
            } else return 0.0;
        } else return 0.0;
    }

    @Override
    public Variable getVariable() {
        return lambda;
    }

    @Override
    public double getValue() {
        return 0.0;
    }

    @Override
    public void setValue(double newConstVar) {
    }

    @Override
    public double getStartVarValue() {
        return 0.0;
    }

    @Override
    public String toString() {
        return lambda.getType().name() + lambda.getId() + " * ( ( " + xEnd.getType().name() + xEnd.getId() +
                " - " + xStart.getType().name() + xStart.getId() + ") ^ 2 + ( " + yEnd.getType().name() + yEnd.getId() +
                " - " + yStart.getType().name() + yStart.getId() + " ) ^ 2  - " + fixLength + " ^ 2 )";
    }
}
