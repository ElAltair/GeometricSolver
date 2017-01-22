package geometric_solver.geometry;

import geometric_solver.math.*;
import geometric_solver.math.constraints.FixAxis;
import javafx.PointContextMenu;
import javafx.Pos;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.get;

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
    private ArrayList<Constraint> pointConstraints;
    private double startVaueX;
    private double startVaueY;
    private NewtonSolver newtonSolver;
    private Source source;
    private Pane root;

    public Point(double x, double y) {
        super(x, y, 4.0);
        lagrangeComponents = new ArrayList<>();
        PointContextMenu pointContextMenu = new PointContextMenu();
        pointConstraints = new ArrayList<>();

        oldPoint = new Pos();

        //squaredSummX = SquaredDiff.build(x, oldPoint.getX());
        //squaredSummY = SquaredDiff.build(y, oldPoint.getY());

        // TODO ONLY FOR FUCKING DEBUG PURPOSE! REMOVE THIS HARDCODE
        squaredSummX = SquaredDiff.build(x, oldPoint.getX());
        squaredSummY = SquaredDiff.build(y, oldPoint.getY());
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

        this.setOnDragDetected(event -> {
            setOldPoint(new Pos(event.getSceneX() - this.getCenterX(), event.getSceneY() - this.getCenterY()));
        });

        this.setOnMouseDragged(event -> {
            double ofsetX = event.getSceneX();
            double ofsetY = event.getSceneY();
            double newPosX = ofsetX + oldPoint.getX();
            double newPosY = ofsetY + oldPoint.getY();
            Point point = (Point) event.getSource();
            point.updateLagrangeComponents(newPosX, newPosY);
            newtonSolver.solve();
            updateObjectOnScene();
        });

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

                        Stage dialog = new Stage();
                        dialog.setWidth(230);
                        dialog.setHeight(70);
                        dialog.initStyle(StageStyle.UTILITY);
                        TextField value = new TextField();
                        value.setPrefWidth(160);
                        value.setPadding(new Insets(10, 10, 10, 10));
                        value.setText("Enter your value");
                        Button submitValue = new Button("Submit");
                        submitValue.setPrefWidth(70);
                        submitValue.setPadding(new Insets(10, 10, 10, 10));
                        submitValue.setAlignment(javafx.geometry.Pos.CENTER);
                        submitValue.setOnAction(submit -> {
                            try {
                                this.fixAxis(Axis.AXIS_Y, new Double(value.getText()));
                                System.out.println("Fixed Y with:" + new Double(value.getText()));
                                dialog.close();
                                lagrange.updateLabel();
                            } catch (Exception e) {
                                value.setText("WRONG VALUE");
                            }
                        });
                        GridPane gridPane = new GridPane();
                        gridPane.add(value, 0, 0);
                        gridPane.setPrefWidth(300);
                        gridPane.setPrefHeight(100);
                        gridPane.add(submitValue, 1, 0);
                        Scene scene = new Scene(gridPane);
                        dialog.setScene(scene);
                        dialog.setTitle("Enter value for constraint");
                        dialog.show();

                    });
                    axisMenu.getItems().addAll(enterValue, chooseCurrent);
                    axisMenu.show(this, event.getScreenX(), event.getScreenY());

                });
                fixX.setOnAction(innerMenuClickedY -> {
                    ContextMenu axisMenu = new ContextMenu();
                    MenuItem enterValue = new MenuItem("Enter Value");
                    MenuItem chooseCurrent = new MenuItem("Choose Current");
                    chooseCurrent.setOnAction(chooseCurrentEvent -> {
                        this.fixAxis(Axis.AXIS_X, getCenterX());
                        System.out.println("Fixed Current X!");
                        lagrange.updateLabel();
                    });
                    enterValue.setOnAction(enterValueEvent -> {

                        Stage dialog = new Stage();
                        dialog.setWidth(230);
                        dialog.setHeight(70);
                        dialog.initStyle(StageStyle.UTILITY);
                        TextField value = new TextField();
                        value.setPrefWidth(160);
                        value.setPadding(new Insets(10, 10, 10, 10));
                        value.setText("Enter your value");
                        Button submitValue = new Button("Submit");
                        submitValue.setPrefWidth(70);
                        submitValue.setPadding(new Insets(10, 10, 10, 10));
                        submitValue.setAlignment(javafx.geometry.Pos.CENTER);
                        submitValue.setOnAction(submit -> {
                            try {
                                this.fixAxis(Axis.AXIS_X, new Double(value.getText()));
                                System.out.println("Fixed X with:" + new Double(value.getText()));
                                dialog.close();
                                lagrange.updateLabel();
                            } catch (Exception e) {
                                value.setText("WRONG VALUE");
                            }
                        });
                        GridPane gridPane = new GridPane();
                        gridPane.add(value, 0, 0);
                        gridPane.setPrefWidth(300);
                        gridPane.setPrefHeight(100);
                        gridPane.add(submitValue, 1, 0);
                        Scene scene = new Scene(gridPane);
                        dialog.setScene(scene);
                        dialog.setTitle("Enter value for constraint");
                        dialog.show();

                    });
                    axisMenu.getItems().addAll(enterValue, chooseCurrent);
                    axisMenu.show(this, event.getScreenX(), event.getScreenY());

                });
                fixAxisContextMenu.getItems().addAll(fixX, fixY);
                fixAxisContextMenu.show(this, event.getScreenX(), event.getScreenY());
            });
            pointContextMenu.menuItems.get("showConstraints").setOnAction(event1 -> {
                Stage table = new Stage();
                table.setTitle("Point Constraints");
                GridPane gridPane = new GridPane();
                gridPane.setGridLinesVisible(true);
                for (int i = 0; i < pointConstraints.size(); i++) {
                    Differentiable pointConstraint = pointConstraints.get(i);
                    gridPane.add(new Text(pointConstraint.toString()),0,i);
                    Button removeButton = new Button("Remove");
                    removeButton.setOnAction(event2 -> {
                        //TODO REMOVE CONSTRAINT
                        pointConstraints.remove(pointConstraint);
                        table.close();
                        pointContextMenu.menuItems.get("showConstraints").fire();
                        System.out.println("HAVEN'T REMOVED");
                    });
                    gridPane.add(removeButton, 1, i);
                }
                Scene scene = new Scene(gridPane);
                if (pointConstraints.size() == 0) {
                table.setScene(new Scene(new TextField("There is no constraints!")));
                } else {
                    table.setScene(scene);
                }
                table.show();
            });
            pointContextMenu.show(this, event.getScreenX(), event.getScreenY());
        });

        activateDragging(true);

    }

    public void setLagrange(Lagrange lagrange) {
        this.lagrange = lagrange;
        lagrange.addComponents(this.getLagrangeComponents());
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
        if (fixAxis == Axis.AXIS_X) {
            FixAxis fixXAxis = new FixAxis(fixAxis, value, squaredSummX.getVariable());
            pointConstraints.add(fixXAxis);
            lagrange.addConstraint(fixXAxis);
        }
        else if (fixAxis == Axis.AXIS_Y) {
            FixAxis fixYAxis = new FixAxis(fixAxis, value, squaredSummY.getVariable());
            pointConstraints.add(fixYAxis);
            lagrange.addConstraint(fixYAxis);
        }
        else
            throw new IllegalArgumentException("Can't create constraint - FixAxis, for point "
                    + this.toString() + "wrong axis");

    }

    public ArrayList<Constraint> getConstrains() {
        return pointConstraints;
    }

    public void activateDragging(boolean status) {
        if (status) {
            this.setOnMouseClicked(clickedEvent);
        } else
            this.setOnMouseDragged(null);
    }

    public void onMouseRelease(EventHandler<MouseEvent> event) {
        this.setOnMouseReleased(event);
    }

    public void onMouseDraged(EventHandler<MouseEvent> event) {
        this.setOnMouseDragged(event);
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

    public void updateLagrangeComponents(double x, double y) {
        lagrangeComponents.get(0).setValue(x);
        lagrangeComponents.get(1).setValue(y);
    }

    public ArrayList<Differentiable> getLagrangeComponents() {
        return lagrangeComponents;
    }

    public SquaredDiff getSquaredSummX() {
        return squaredSummX;
    }

    public SquaredDiff getSquaredSummY() {
        return squaredSummY;
    }

    private void updateObjectOnScene() {
        root.getChildren().stream().filter((elem) -> elem instanceof Point).forEach((elem) -> {
            Point point = (Point) elem;
            point.setCenterX(source.getValue(point.getSquaredSummX().getVariable()));
            point.setCenterY(source.getValue(point.getSquaredSummY().getVariable()));
            source.setVariable(point.getSquaredSummX().getVariable(), point.getX());
            source.setVariable(point.getSquaredSummY().getVariable(), point.getY());
        });
    }

    public void setNewtonSolver(NewtonSolver newtonSolver) {
        this.newtonSolver = newtonSolver;
    }

    public NewtonSolver getNewtonSolver() {
        return newtonSolver;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public Pane getRoot() {
        return root;
    }
}
