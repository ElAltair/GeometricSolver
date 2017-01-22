package geometric_solver.math;

import geometric_solver.math.constraints.FixAxis;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lagrange {
    private ArrayList<Differentiable> functonParts;
    private Source source;
    private Label lagrangeLabel;

    public void setLagrangeLabel(Label lagrangeLabel) {
        this.lagrangeLabel = lagrangeLabel;
    }

    public Lagrange(Source source) {
        functonParts = new ArrayList<>();
        this.source = source;
    }

    //Не понимаю, почему здесь нельзя передавать Differentiable
    public void addComponents(ArrayList<Differentiable> pointComponenst) {
        for (Differentiable it : pointComponenst) {
            functonParts.add(it);
            source.add(it.getVariable(), it.getStartVarValue());
        }
    }

    //TODO remove? need to think
    /*
    public void addComponentsAxis(Differentiable axisComponenst) {
        functonParts.add(axisComponenst);
        source.add(axisComponenst.getVariable(),axisComponenst.getValue());

    }
    */

    // TODO remove? need to think
    /*
    public void changePosition(double pointValueX, double pointValueY) {
        Variable pointVarX = new Variable(Variable.generateID(VariableType.X), VariableType.X);
        SquaredDiff squaredX = new SquaredDiff(pointVarX, pointValueX);
        source.add(pointVarX, pointValueX);
        functonParts.add(squaredX);

        Variable pointVarY = new Variable(Variable.generateID(VariableType.X), VariableType.X);
        SquaredDiff squaredY = new SquaredDiff(pointVarY, pointValueY);
        source.add(pointVarY, pointValueY);
        functonParts.add(squaredY);
    }
    */

    public Lagrange addConstraint(Constraint constraint) {
        functonParts.add(constraint);
        source.add(constraint.getVariable(), 0.0);
        return this;
    }

    public void addConstraint(ArrayList<Constraint> constraintList) {
        for (Constraint it : constraintList) {
            functonParts.add(it);
            source.add(it.getVariable(), 0.0);
        }
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
            fullString += it.toString() + " + ";
        }
        return fullString;
    }

    public List<Differentiable> getFunctionParts() {
        return Collections.unmodifiableList(functonParts);
    }

    public void updateLabel() {
        lagrangeLabel.setText(this.print());
    }

    public int getSize() {
        return functonParts.size();
    }
}
