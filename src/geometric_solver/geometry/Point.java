package geometric_solver.geometry;

import geometric_solver.math.Constraint;
import geometric_solver.math.Differentiable;
import geometric_solver.math.Lagrange;
import geometric_solver.math.SquaredDiff;
import geometric_solver.math.constraints.FixAxis;
import javafx.PointContextMenu;
import javafx.Pos;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private Lagrange lagrange;
    private TextField constraintValue;

    public void setLagrange(Lagrange lagrange) {
        this.lagrange = lagrange;
    }

    private ArrayList<Constraint> pointConstraints;

    private double startVaueX;
    private double startVaueY;

    public Point(double x, double y) {
        super(x, y, 4.0);
        lagrangeComponents = new ArrayList<>();
        PointContextMenu pointContextMenu = new PointContextMenu();
        pointConstraints = new ArrayList<>();

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
                lagrange.updateLabel();
            });
            pointContextMenu.menuItems.get("fixAxis").setOnAction(menuClicked -> {
                ContextMenu fixAxisContextMenu = new ContextMenu();
                MenuItem fixY = new MenuItem("Fix Y");
                MenuItem fixX = new MenuItem("Fix X");
                fixY.setOnAction(innerMenuClickedY -> {
                    ContextMenu axisMenu = new ContextMenu();
                    MenuItem enterValue = new MenuItem("Enter Value");
                    MenuItem chooseCurrent = new MenuItem("Choose Current");
                    chooseCurrent.setOnAction(chooseCurrentEvent -> {
                        this.fixAxis(Axis.AXIS_Y, getCenterY());
                        System.out.println("Fixed Current Y!");
                        lagrange.updateLabel();
                    });
                    enterValue.setOnAction(enterValueEvent -> {
//                        AnchorPane anchorPane = new AnchorPane();
//                        anchorPane.setPrefHeight(50);
//                        anchorPane.setPrefWidth(170);
//                        GridPane gridPane = new GridPane();
//                        anchorPane.getChildren().add(gridPane);
//                        TextField constraintValue = new TextField();
//                        constraintValue.setPrefWidth(100);
//                        gridPane.add(constraintValue, 0, 0);
//                        ButtonType submitValue = new ButtonType("Submit");
//                        submitValue.setPrefWidth(70);
//                        gridPane.add(submitValue, 1, 0);
//                        constraintValue.setEditable(true);
//                        submitValue.setOnAction(event1 -> {
//                            System.out.println("Entered: " + constraintValue.getText());
//                        });
//                        Dialog<String> dialog = new Dialog<String>();
//                        ButtonType submitValue = new ButtonType("Submit", ButtonBar.ButtonData.APPLY);
//                        dialog.getDialogPane().getChildren().add(new TextField());
//                        dialog.getDialogPane().getButtonTypes().add(submitValue);
                            this.fixAxis(Axis.AXIS_Y, new Double(getConstraintValue().getText()));
                            System.out.println("Fixed Y : " + new Double(getConstraintValue().getText()) + "!");
                            lagrange.updateLabel();
                    });
                    axisMenu.getItems().addAll(enterValue, chooseCurrent);
                    axisMenu.show(this, event.getScreenX(), event.getScreenY());

                });
                fixX.setOnAction(innerMenuClickedX -> {
                    this.fixAxis(Axis.AXIS_X, getCenterX());
                    System.out.println("Fixed X!");
                    lagrange.updateLabel();
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

    public void fixAxis(Axis fixAxis, double value) {
        if (fixAxis == Axis.AXIS_X)
            pointConstraints.add(new FixAxis(fixAxis, value, squaredSummX.getVariable()));
        else if (fixAxis == Axis.AXIS_Y)
            pointConstraints.add(new FixAxis(fixAxis, value, squaredSummY.getVariable()));
        else
            throw new IllegalArgumentException("Can't create constraint - FixAxis, for point "
                    + this.toString() + "wrong axis");
    }

    public ArrayList<Constraint> getConstrains() {
        return pointConstraints;
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

    public void setConstraintValue(TextField constraintValue) {
        this.constraintValue = constraintValue;
    }

    public TextField getConstraintValue() {
        return constraintValue;
    }
}
