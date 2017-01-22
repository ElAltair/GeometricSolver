package geometric_solver.geometry;

import geometric_solver.math.Constraint;
import geometric_solver.math.Differentiable;
import geometric_solver.math.SquaredDiff;
import geometric_solver.math.constraints.FixLength;
import javafx.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineJoin;

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
        this.setStrokeWidth(3);

        this.setOnMouseExited((event) -> {
            Line tLine = (Line) event.getSource();
            tLine.setStroke(Color.GREEN);
        });


        this.getP1().setOnMouseDragged(event -> {
            double offsetX = event.getSceneX();
            double offsetY = event.getSceneY();
            double newPosX = offsetX + p1.getOldPoint().getX();
            double newPosY = offsetY + p1.getOldPoint().getY();
            Circle c = ((Circle) event.getSource());
            c.setCenterX(newPosX);
            c.setCenterY(newPosY);

            length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
            this.setStartX(p1.getX());
            this.setEndX(p2.getX());
            this.setStartY(p1.getY());
            this.setEndY(p2.getY());
        });

        this.getP2().setOnMouseDragged(event -> {
            double ofsetX = event.getSceneX();
            double ofsetY = event.getSceneY();
            double newPosX = ofsetX + p2.getOldPoint().getX();
            double newPosY = ofsetY + p2.getOldPoint().getY();
            Circle c = ((Circle) event.getSource());
            c.setCenterX(newPosX);
            c.setCenterY(newPosY);

            length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
            this.setStartX(p1.getX());
            this.setEndX(p2.getX());
            this.setStartY(p1.getY());
            this.setEndY(p2.getY());
        });

        Pos oldEvent = new Pos(0,0);

        this.setOnMouseEntered(event -> {
            oldEvent.setX(0);
            oldEvent.setY(0);
            this.setStroke(Color.BLUE);
        });

        // New line after drag
        this.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - (oldEvent.getX() == 0 ? event.getSceneX() : oldEvent.getX());
            double offsetY = event.getSceneY() - (oldEvent.getY() == 0 ? event.getSceneY() : oldEvent.getY());

            length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
            this.setStartX(this.getStartX() + offsetX);
            this.setEndX(this.getEndX()+ offsetX);
            this.setStartY(this.getStartY()+ offsetY);
            this.setEndY(this.getEndY()+ offsetY);
            p1.setCenterX(this.getStartX());
            p1.setCenterY(this.getStartY());
            p2.setCenterX(this.getEndX());
            p2.setCenterY(this.getEndY());
            oldEvent.setX(event.getSceneX());
            oldEvent.setY(event.getSceneY());
        });
        this.setOnDragExited(event -> {
            p1.setCenterX(this.getStartX());
            p1.setCenterY(this.getStartY());
            p2.setCenterX(this.getEndX());
            p2.setCenterY(this.getEndY());
        });

        this.setOnContextMenuRequested(event -> {
            System.out.println("Context Menu shit");
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

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public ArrayList<Differentiable> getLagrangeComponents() {
        ArrayList<Differentiable> returnArray = new ArrayList<>();
        returnArray.addAll(p1.getLagrangeComponents());
        returnArray.addAll(p2.getLagrangeComponents());
        return returnArray;
    }

}
