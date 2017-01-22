package geometric_solver.geometry;

import geometric_solver.math.*;
import geometric_solver.math.constraints.FixLength;
import javafx.LineContextMenu;
import javafx.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.naming.Context;
import java.util.ArrayList;

public class Line extends javafx.scene.shape.Line {
    private double length;
    private Point p1;
    private Point p2;
    private LineContextMenu lineContextMenu;
    private Lagrange lagrange;
    private Pane root;
    private NewtonSolver newtonSolver;
    private Source source;
    private ArrayList<Constraint> lineConstraints;

    public Line(Point p1, Point p2) {
        super(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        this.p1 = p1;
        this.p2 = p2;
        lineConstraints = new ArrayList<>();
        lineContextMenu = new LineContextMenu();
        length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
        this.setStrokeWidth(3);

        this.setOnMouseExited((event) -> {
            Line tLine = (Line) event.getSource();
            tLine.setStroke(Color.GREEN);
        });


        this.getP1().setOnMouseDragged(event -> {
            double ofsetX = event.getSceneX();
            double ofsetY = event.getSceneY();
            double newPosX = ofsetX + p2.getOldPoint().getX();
            double newPosY = ofsetY + p2.getOldPoint().getY();
            Point point = (Point) event.getSource();
            point.updateLagrangeComponents(newPosX, newPosY);
            newtonSolver.solve();
            updateObjectOnScene();
            length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
        });

        this.getP2().setOnMouseDragged(event -> {
            double ofsetX = event.getSceneX();
            double ofsetY = event.getSceneY();
            double newPosX = ofsetX + p2.getOldPoint().getX();
            double newPosY = ofsetY + p2.getOldPoint().getY();
            Point point = (Point) event.getSource();
            point.updateLagrangeComponents(newPosX, newPosY);
            newtonSolver.solve();
            updateObjectOnScene();
            length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
        });

        Pos oldEvent = new Pos(0, 0);

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
            p1.updateLagrangeComponents(this.getStartX() + offsetX, this.getStartY() + offsetY);
            p2.updateLagrangeComponents(this.getEndX() + offsetX, this.getEndY() + offsetY);
            newtonSolver.solve();
            updateObjectOnScene();
            oldEvent.setX(event.getSceneX());
            oldEvent.setY(event.getSceneY());
        });

        this.setOnContextMenuRequested(event -> {
            lineContextMenu.menuItems.get("fixFull").setOnAction(menuEvent -> {
                p1.fixAxis(Axis.AXIS_X, p1.getCenterX());
                p1.fixAxis(Axis.AXIS_Y, p1.getCenterY());
                p2.fixAxis(Axis.AXIS_X, p2.getCenterX());
                p2.fixAxis(Axis.AXIS_Y, p2.getCenterY());
                System.out.println("Fully fixed Line!");
                lagrange.updateLabel();
            });
            lineContextMenu.menuItems.get("fixAxis").setOnAction(menuClicked -> {
                ContextMenu fixAxisContextMenu = new ContextMenu();
                MenuItem fixY = new MenuItem("Fix Y");
                MenuItem fixX = new MenuItem("Fix X");
                fixY.setOnAction(innerMenuClickedY -> {
                    p1.fixAxis(Axis.AXIS_Y, p1.getCenterY());
                    p2.fixAxis(Axis.AXIS_Y, p2.getCenterY());
                    System.out.println("Fixed Current Y!");
                    lagrange.updateLabel();
                });
                fixX.setOnAction(innerMenuClickedY -> {
                    p1.fixAxis(Axis.AXIS_X, p1.getCenterX());
                    p2.fixAxis(Axis.AXIS_X, p2.getCenterX());
                    System.out.println("Fixed Current X!");
                    lagrange.updateLabel();
                });
                fixAxisContextMenu.getItems().addAll(fixX, fixY);
                fixAxisContextMenu.show(this, event.getScreenX(), event.getScreenY());
            });
            lineContextMenu.menuItems.get("fixLength").setOnAction(menuClicked -> {
                ContextMenu lengthMenu = new ContextMenu();
                MenuItem enterValue = new MenuItem("Enter Value");
                MenuItem chooseCurrent = new MenuItem("Choose Current");
                chooseCurrent.setOnAction(chooseCurrentEvent -> {
                    this.fixLength(length);
                    System.out.println("Fixed Current length!");
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
                            this.fixLength(new Double(value.getText()));
                            System.out.println("Fixed line length with:" + new Double(value.getText()));
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
                lengthMenu.getItems().addAll(enterValue, chooseCurrent);
                lengthMenu.show(this, event.getScreenX(), event.getScreenY());
            });
            lineContextMenu.menuItems.get("fixAngle").setOnAction(event1 -> {
                ContextMenu angleMenu = new ContextMenu();
                MenuItem horizontal = new MenuItem("Horizontal");
                MenuItem vertical = new MenuItem("Vertical");
                MenuItem choose = new MenuItem("Choose angle");

                horizontal.setOnAction(event2 -> {
                    this.fixAngle(90);
                    System.out.println("Fixed horizontal!");
                });
                vertical.setOnAction(event2 -> {
                    this.fixAngle(0);
                    System.out.println("Fixed vertical!");
                });
                choose.setOnAction(event2 -> {
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
                            this.fixAngle(new Double(value.getText()));
                            System.out.println("Fixed line angle with:" + new Double(value.getText()));
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

                angleMenu.getItems().addAll(horizontal, vertical, choose);
                angleMenu.show(this, event.getScreenX(), event.getScreenY());
            });
            lineContextMenu.menuItems.get("showConstraints").setOnAction(event1 -> {
                Stage table = new Stage();
                table.setTitle("Line Constraints");
                GridPane gridPane = new GridPane();
                gridPane.setGridLinesVisible(true);
                for (int i = 0; i < lineConstraints.size(); i++) {
                    Differentiable lineConstraint = lineConstraints.get(i);
                    gridPane.add(new Text(lineConstraint.toString()),0,i);
                    Button removeButton = new Button("Remove");
                    removeButton.setOnAction(event2 -> {
                        //TODO REMOVE CONSTRAINT
                        lineConstraints.remove(lineConstraint);
                        table.close();
                        lineContextMenu.menuItems.get("showConstraints").fire();
                        System.out.println("HAVEN'T REMOVED");
                    });
                    gridPane.add(removeButton, 1, i);
                }
                for (int i = 0; i < p1.getConstrains().size(); i++) {
                    Differentiable pointConstraint = p1.getConstrains().get(i);
                    gridPane.add(new Text(pointConstraint.toString()),0,i + lineConstraints.size());
                    Button removeButton = new Button("Remove");
                    removeButton.setOnAction(event2 -> {
                        //TODO REMOVE CONSTRAINT
                        p1.getConstrains().remove(pointConstraint);
                        table.close();
                        lineContextMenu.menuItems.get("showConstraints").fire();
                        System.out.println("HAVEN'T REMOVED");
                    });
                    gridPane.add(removeButton, 1, i + lineConstraints.size());
                }
                for (int i = 0; i < p2.getConstrains().size(); i++) {
                    Differentiable pointConstraint = p2.getConstrains().get(i);
                    gridPane.add(new Text(pointConstraint.toString()),0,i + p1.getConstrains().size() + lineConstraints.size());
                    Button removeButton = new Button("Remove");
                    removeButton.setOnAction(event2 -> {
                        //TODO REMOVE CONSTRAINT
                        p2.getConstrains().remove(pointConstraint);
                        table.close();
                        lineContextMenu.menuItems.get("showConstraints").fire();
                        System.out.println("HAVEN'T REMOVED");
                    });
                    gridPane.add(removeButton, 1, i + p1.getConstrains().size() + lineConstraints.size());
                }
                Scene scene = new Scene(gridPane);
                if (lineConstraints.size() + p1.getConstrains().size() + p2.getConstrains().size() == 0) {
                    table.setScene(new Scene(new TextField("There is no constraints!")));
                } else {
                    table.setScene(scene);
                }
                table.show();
            });
            lineContextMenu.show(this, event.getScreenX(), event.getScreenY());
        });
    }

    public void fixAngle(double angle) {
        //TODO Констрейнт на угол angle - значение в ГРАДУСАХ
    }


    public void fixLength(double value) {
        FixLength fixLength = new FixLength(p1.getSquaredSummX().getVariable(), p1.getSquaredSummY().getVariable(),
                p2.getSquaredSummX().getVariable(), p2.getSquaredSummY().getVariable(), value);
        lineConstraints.add(fixLength);
        lagrange.addConstraint(fixLength);
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

    public Lagrange getLagrange() {
        return lagrange;
    }

    public void setLagrange(Lagrange lagrange) {
        this.lagrange = lagrange;
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public NewtonSolver getNewtonSolver() {
        return newtonSolver;
    }

    public void setNewtonSolver(NewtonSolver newtonSolver) {
        this.newtonSolver = newtonSolver;
    }

    private void updateObjectOnScene() {
        root.getChildren().stream().filter((elem) -> elem instanceof Point).forEach((elem) -> {
            Point point = (Point) elem;
            point.setCenterX(source.getValue(point.getSquaredSummX().getVariable()));
            point.setCenterY(source.getValue(point.getSquaredSummY().getVariable()));
            source.setVariable(point.getSquaredSummX().getVariable(), point.getX());
            source.setVariable(point.getSquaredSummY().getVariable(), point.getY());
        });
        root.getChildren().stream().filter((elem) -> elem instanceof Line).forEach((elem) -> {
            Line line = (Line) elem;
            line.setStartX((source.getValue(line.getP1().getSquaredSummX().getVariable())));
            line.setStartY((source.getValue(line.getP1().getSquaredSummY().getVariable())));
            line.setEndX((source.getValue(line.getP2().getSquaredSummX().getVariable())));
            line.setEndY((source.getValue(line.getP2().getSquaredSummY().getVariable())));
            source.setVariable(line.getP1().getSquaredSummX().getVariable(), line.getP1().getX());
            source.setVariable(line.getP1().getSquaredSummY().getVariable(), line.getP1().getY());
            source.setVariable(line.getP2().getSquaredSummX().getVariable(), line.getP2().getX());
            source.setVariable(line.getP2().getSquaredSummY().getVariable(), line.getP2().getY());
        });
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
