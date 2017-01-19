package geometric_solver.geometry;

import geometric_solver.math.Constraint;
import geometric_solver.math.SquaredSumm;
import geometric_solver.math.constraints.FixLength;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Line extends javafx.scene.shape.Line {
    private double length;
    private Point p1;
    private Point p2;

    public Line(Point p1, Point p2) {
        super(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        this.p1 = p1;
        this.p2 = p2;
        length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));


        this.setOnMouseEntered(((event) -> {
            Line tLine = (Line) event.getSource();
            tLine.setStroke(Color.BLUE);
        }));
        this.setOnMouseExited((event) -> {
            Line tLine = (Line) event.getSource();
            tLine.setStroke(Color.GREEN);
        });
    }

    public Line(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));

        this.setOnMouseEntered(((event) -> {
            Line tLine = (Line) event.getSource();
            tLine.setStroke(Color.BLUE);
        }));
        this.setOnMouseExited((event) -> {
            Line tLine = (Line) event.getSource();
            tLine.setStroke(Color.GREEN);
        });
    }


    public static Constraint fixLength(double value) {
        return new FixLength();
    }

    public ArrayList<SquaredSumm> getLagrangeComponents() {
        ArrayList<SquaredSumm> returnArray = new ArrayList<>();
        returnArray.addAll(p1.getLagrangeComponents());
        returnArray.addAll(p2.getLagrangeComponents());
        return returnArray;
    }

}
