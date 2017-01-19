package geometric_solver.geometry;

import geometric_solver.math.Constraint;

public class Line {
    double length;
    private Point p1;
    private Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
    }

    public Line(double x1, double y1, double x2, double y2) {
        Point p1 = new Point(x1, y1);
        this.p1 = p1;
        Point p2 = new Point(x2, y2);
        this.p2 = p2;
        length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
    }

    /*
    public static Constraint fixLength(double value) {
        return new Constraint();
    }
    */
}
