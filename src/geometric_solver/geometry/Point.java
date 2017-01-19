package geometric_solver.geometry;

import geometric_solver.geometry.Axis;
import geometric_solver.math.Constraint;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*
    public static Constraint fullFix() {
        return new Constraint();
    }

    public static Constraint fixAxis(Axis axis) {
        if (axis == Axis.AXIS_X)
            return new Constraint();
        else if (axis == Axis.AXIS_Y)
            return new Constraint();
        else if (axis == Axis.AXIS_Z)
            return new Constraint();
        else
            throw new IllegalArgumentException("Enumeration value of AXIS isn't presented");

    }

    public static Constraint fixAxises(Axis axis_one, Axis axis_two) {
        if (axis_one == Axis.AXIS_X && axis_two == Axis.AXIS_X)
            return new Constraint();
        else
            throw new IllegalArgumentException("Enumeration value of AXIS isn't presented");

    }

    public static Constraint fixAxises(Axis axis_one, Axis axis_two, Axis axis_three) {
        if (axis_one == Axis.AXIS_X && axis_two == Axis.AXIS_X)
            return new Constraint();
        else
            throw new IllegalArgumentException("Enumeration value of AXIS isn't presented");
    }
    */

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
