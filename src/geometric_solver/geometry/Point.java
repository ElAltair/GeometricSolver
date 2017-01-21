package geometric_solver.geometry;

import geometric_solver.math.Constraint;
import geometric_solver.math.Differentiable;
import geometric_solver.math.SquaredDiff;
import geometric_solver.math.constraints.FixAxis;
import javafx.PointContextMenu;
import javafx.Pos;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

import static java.awt.SystemColor.menu;

public class Point extends Circle {

    private final double size = 4.0;
    private Pos oldPoint;
    private SquaredDiff squaredSummX;
    private SquaredDiff squaredSummY;
    private PointContextMenu pointContextMenu;
    private EventHandler<MouseEvent> dragEvent;
    private EventHandler<MouseEvent> clickedEvent;
    private EventHandler<MouseEvent> releaseEvent;
    private ArrayList<Differentiable> lagrangeComponents;

    private double startVaueX;
    private double startVaueY;

    public Point(double x, double y) {
        super(x, y, 4.0);
        lagrangeComponents = new ArrayList<>();
        PointContextMenu pointContextMenu = new PointContextMenu();
        oldPoint = new Pos();

        //squaredSummX = SquaredDiff.build(x, oldPoint.getX());
        //squaredSummY = SquaredDiff.build(y, oldPoint.getY());
        // TODO ONLY FOR FUCKING DEBUG PURPOSE! REMOVE THIS HARDCODE
        squaredSummX = SquaredDiff.build(x, 199.0);
        squaredSummY = SquaredDiff.build(y, 152.0);
        lagrangeComponents.add(squaredSummX);
        lagrangeComponents.add(squaredSummY);



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
            squaredSummX.setValue(this.getCenterX());
            squaredSummY.setValue(this.getCenterY());
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
            pointContextMenu.menuItems.get("fixFull").setOnAction(menuClicked -> {
                this.fixAxis(Axis.AXIS_X, getCenterX());
                this.fixAxis(Axis.AXIS_Y, getCenterY());
                System.out.println("Fixed coords!");
            });
            pointContextMenu.menuItems.get("fixAxis").setOnAction(menuClicked -> {
                ContextMenu fixAxisContextMenu = new ContextMenu();
                MenuItem fixY = new MenuItem("Fix Y");
                MenuItem fixX = new MenuItem("Fix X");
                fixY.setOnAction(innerMenuClickedY -> {
                    this.fixAxis(Axis.AXIS_Y, getCenterY());
                    System.out.println("Fixed Y!");
                });
                fixX.setOnAction(innerMenuClickedX -> {
                    this.fixAxis(Axis.AXIS_X, getCenterX());
                    System.out.println("Fixed X!");
                });
                fixAxisContextMenu.getItems().addAll(fixX, fixY);
                fixAxisContextMenu.show(this, event.getScreenX(), event.getScreenY());
            });
            pointContextMenu.show(this, event.getScreenX(), event.getScreenY());
        });

        activateDragging(true);

    }

    public Pos getOldPoint() {
        return oldPoint;
    }

    public void setOldPoint(Pos oldPos) {
        oldPoint = oldPos;
    }

    /*
    public void move(double newX, double newY) {
        visualizationObject.setCenterX(newX);
        visualizationObject.setCenterY(newY);
        squaredSummX = SquaredDiff.build(newX);
        squaredSummY = SquaredDiff.build(newY);
    }
    */

    public Constraint fixAxis(Axis fixAxis, double value) {
        if (fixAxis == Axis.AXIS_X)
            return new FixAxis(fixAxis, value, squaredSummX.getVariable());
        else if (fixAxis == Axis.AXIS_Y)
            return new FixAxis(fixAxis, value, squaredSummY.getVariable());
        else
            throw new IllegalArgumentException("Can't create constraint - FixAxis, for point "
                    + this.toString() + "wrong axis");
    }

    public void activateDragging(boolean status) {
        if (status) {
            this.setOnMouseDragged(dragEvent);
            this.setOnMouseClicked(clickedEvent);
        } else
            this.setOnMouseDragged(null);
    }

    public void onMouseRelease(EventHandler<MouseEvent> event) {
        this.setOnMouseReleased(event);
    }

    public double getX() {
        return this.getCenterX();
    }

    public double getY() {
        return this.getCenterY();
    }

    public void updateLagrangeComponents() {
        lagrangeComponents.get(0).setValue(this.getCenterX());
        lagrangeComponents.get(1).setValue(this.getCenterY());
    }

    public ArrayList<Differentiable> getLagrangeComponents() {
        return lagrangeComponents;
    }

}
