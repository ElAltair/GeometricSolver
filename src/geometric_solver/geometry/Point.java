package geometric_solver.geometry;

import geometric_solver.math.Differentiable;
import geometric_solver.math.SquaredSumm;
import javafx.Pos;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Point extends Circle {

    private Pos oldPoint;
    private double size = 4.0;
    private SquaredSumm squaredSummX;
    private SquaredSumm squaredSummY;
    private EventHandler<MouseEvent> dragEvent;
    private EventHandler<MouseEvent> clickedEvent;
    private EventHandler<MouseEvent> releaseEvent;
    private ArrayList<Differentiable> lagrangeComponents;

    public Pos getOldPoint() {
        return oldPoint;
    }

    public Point(double x, double y) {
        super(x, y, 4.0);

        lagrangeComponents = new ArrayList<>();
        squaredSummX = SquaredSumm.build(x);
        squaredSummY = SquaredSumm.build(y);
        lagrangeComponents.add(squaredSummX);
        lagrangeComponents.add(squaredSummY);


        oldPoint = new Pos();

        clickedEvent = event -> {
            Circle source = (Circle) event.getSource();
            double nodeX = event.getX();
            double nodeY = event.getY();
            double deltaX = this.getScene().getX() - event.getSceneX();
            double deltaY = this.getScene().getY() - event.getSceneY();
            double pointPosX = 0.0;
            double pointPosY = 0.0;

            pointPosX = event.getSceneX() - ((Circle) event.getSource()).getCenterX();
            pointPosY = event.getSceneY() - ((Circle) event.getSource()).getCenterY();
            System.out.println("POINT");

            oldPoint.setX(pointPosX);
            oldPoint.setY(pointPosY);
        };

        dragEvent = event -> {
            double ofsetX = event.getSceneX();
            double ofsetY = event.getSceneY();
            double newPosX = ofsetX + oldPoint.getX();
            double newPosY = ofsetY + oldPoint.getY();
            Circle c = ((Circle) event.getSource());
            c.setCenterX(newPosX);
            c.setCenterY(newPosY);
        };

        releaseEvent = event -> {
            squaredSummX.setValue(getCenterX());
            squaredSummY.setValue(getCenterY());
        };

        this.setOnMouseEntered(((event) -> {
            Circle circle = (Circle) event.getSource();
            circle.setStroke(Color.RED);
        }));
        this.setOnMouseExited((event) -> {
            Circle circle = (Circle) event.getSource();
            circle.setStroke(Color.GREEN);
        });
        this.setOnContextMenuRequested(event -> {
            System.out.println("Context shit");
        });

        activateDragging(true);

    }

    public void move(double newX, double newY) {
        this.setCenterX(newX);
        this.setCenterY(newY);
        squaredSummX = SquaredSumm.build(newX);
        squaredSummY = SquaredSumm.build(newY);
    }

    public void activateDragging(boolean status) {
        if (status) {
            this.setOnMouseDragged(dragEvent);
            this.setOnMouseClicked(clickedEvent);
        }
        else
            this.setOnMouseDragged(null);
    }

    public double getX() {
        return this.getCenterX();
    }

    public double getY() {
        return this.getCenterY();
    }

    public void updateLagrangeComponents() {
        lagrangeComponents.get(0).setValue(getCenterX());
        lagrangeComponents.get(1).setValue(getCenterY());
    }

    public ArrayList<Differentiable> getLagrangeComponents() {
        return lagrangeComponents;
    }

}
