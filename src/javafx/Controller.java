package javafx;

import geometric_solver.geometry.*;
import geometric_solver.math.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Controller {

    private final double R = 3.0;
    private final double strokeSize = 4.0;
    //public Button rectButton;
    public Button clearButton;
    public Button pointButton;
    public Button lineButton;
    public Button lagrangeButton;
    public Label sceneXCoord;
    public Label sceneYCoord;
    public Label lagrangeLabel;

    // Math
    private Lagrange lagrange;
    private Source source;
    private NewtonSolver newtonSolver;
    private Pane root;
    private EventHandler<MouseEvent> onDragEvent;
    private EventHandler<MouseEvent> onPressedEvent;
    private EventHandler<MouseEvent> onMouseMoved;
    private EventHandler<MouseEvent> onCirclePressedEvent;
    private EventHandler<MouseEvent> onCircleDraggedEvent;
    private EventHandler<MouseEvent> onCircleReleasedEvent;
    private EventHandler<MouseEvent> onLineDraggedEvent;
    private boolean catchMouseCoordinates;
    private ArrayList<Shape> createdPoint;
    private ArrayList<Shape> boundPoints;
    private Line tempLineForLineVisualization;
    private ArrayList<Line> tempLineForRectangle;

    private ArrayList<Double> solvedVariables;


    public void init(Pane pane) {
        root = pane;
        catchMouseCoordinates = false;
        createdPoint = new ArrayList<>();
        tempLineForRectangle = new ArrayList<>();
        boundPoints = new ArrayList<>();

        // math init
        source = new Source();
        lagrange = new Lagrange(source);
        lagrange.setLagrangeLabel(lagrangeLabel);
        newtonSolver = new NewtonSolver(lagrange, source);
        //

        drawGrid(50, root);

        root.getScene().setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                catchMouseCoordinates = false;
                root.getScene().setCursor(Cursor.DEFAULT);
                updateMouseClickedHandler(DrawingObjects.NONE);
                root.getChildren().remove(tempLineForLineVisualization);

                // line temp points
                for (Shape s : createdPoint)
                    root.getChildren().remove(s);
                createdPoint.clear();

                // line temp line
                tempLineForLineVisualization = null;

                // bound points
                for (Shape s : boundPoints)
                    root.getChildren().remove(s);
                boundPoints.clear();
            }
        });

        Pos rectPos = new Pos();


        onDragEvent = event -> {
            double ofsetX = event.getSceneX();
            double ofsetY = event.getSceneY();
        };

        Pos oldPoint = new Pos();
        onCirclePressedEvent = event -> {

            // r.setFill(Color.RED);
            Circle source = (Circle) event.getSource();
            double nodeX = event.getX();
            double nodeY = event.getY();
            double deltaX = root.getScene().getX() - event.getSceneX();
            double deltaY = root.getScene().getY() - event.getSceneY();
            double pointPosX = 0.0;
            double pointPosY = 0.0;

            pointPosX = event.getSceneX() - ((Circle) event.getSource()).getCenterX();
            pointPosY = event.getSceneY() - ((Circle) event.getSource()).getCenterY();
            System.out.println("POINT");

            oldPoint.x = pointPosX;
            oldPoint.y = pointPosY;

        };

        onMouseMoved = event -> {
            sceneXCoord.setText(((Double) event.getX()).toString());
            sceneYCoord.setText(((Double) event.getY()).toString());
        };

    }


    public void initDraggingNodes(EventHandler<MouseEvent> mouse, EventHandler<MouseEvent> drag,
                                  EventHandler<MouseEvent> mouseMoved) {
        onPressedEvent = mouse;
        onDragEvent = drag;
        onMouseMoved = mouseMoved;
    }



    public void createLine(ActionEvent actionEvent) {
        catchMouseCoordinates = true;
        root.getScene().setCursor(Cursor.CROSSHAIR);
        updateMouseClickedHandler(DrawingObjects.LINE);

    }

    public void createPoint(ActionEvent actionEvent) {
        catchMouseCoordinates = true;
        root.getScene().setCursor(Cursor.CROSSHAIR);
        updateMouseClickedHandler(DrawingObjects.POINT);

    }


    void updateMouseClickedHandler(DrawingObjects type) {
        if (catchMouseCoordinates) {
            if (type == DrawingObjects.POINT) {
                root.getScene().setOnMouseClicked((e) -> {

                    double xClick = e.getSceneX();
                    double yClick = e.getSceneY();
                    // Circle point = new Circle(xClick, yClick, R);
                    Point point = new Point(xClick, yClick);
                    point.setLagrange(lagrange);
                    point.setRoot(root);
                    point.setSource(source);
                    point.setNewtonSolver(newtonSolver);

                    root.getChildren().add(point);

                });
            } else if (type == DrawingObjects.LINE) {
                root.getScene().setOnMouseClicked((clickedEvent) -> {
                    double xClick = clickedEvent.getSceneX();
                    double yClick = clickedEvent.getSceneY();
                    Point point = new Point(xClick, yClick);
                    point.setLagrange(lagrange);
                    point.onMouseRelease(onCircleReleasedEvent);
                    point.onMouseDraged(onCircleDraggedEvent);
                    point.setRoot(root);
                    point.setSource(source);
                    point.setNewtonSolver(newtonSolver);
                    createdPoint.add(point);
                    root.getChildren().add(point);

                    root.getScene().setOnMouseMoved((moveEvent) -> {
                        double xMousePoss = moveEvent.getSceneX();
                        double yMousePoss = moveEvent.getSceneY();
                        root.getChildren().remove(tempLineForLineVisualization);
                        tempLineForLineVisualization = new Line(((Circle) createdPoint.get(0)).getCenterX(),
                                ((Circle) createdPoint.get(0)).getCenterY(), xMousePoss, yMousePoss);
                        tempLineForLineVisualization.getStrokeDashArray().add(strokeSize);
                        root.getChildren().add(tempLineForLineVisualization);
                    });
                    if (createdPoint.size() == 2) {
                        //Point point2 = new Point(((Circle) createdPoint.get(1)).getCenterX(), ((Circle) createdPoint.get(1)).getCenterY());
                        //root.getChildren().add(point2);

                        // TODO UNCOMMENT AFTER LINE CHANGES
                         geometric_solver.geometry.Line line = new geometric_solver.geometry.Line(((Point)createdPoint.get(0)), (Point)createdPoint.get(1));
                        line.setLagrange(lagrange);
                        line.setNewtonSolver(newtonSolver);
                        line.setRoot(root);
                        line.setSource(source);

                        //TODO UNCOMMENT AFTER LINE CHANGES
                         line.setStroke(Color.GREEN);


                        //TODO UNCOMMENT AFTER LINE CHANGES
                         root.getChildren().add(line);
                        createdPoint.clear();
                        root.getScene().setOnMouseMoved(null);
                    }
                });
            }
        } else {
            root.getScene().setOnMouseClicked(null);
            root.getScene().setOnMouseMoved(onMouseMoved);
        }
    }

    public void clearAll(ActionEvent actionEvent) {
        root.getChildren().removeIf(elem -> elem instanceof Point || elem instanceof Line);
        init(root);
        lagrangeLabel.setText("");
    }

    public void showLagrangeTable() {
        Stage table = new Stage();
        table.setTitle("Lagrange Components");

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        List<Differentiable> functionParts = lagrange.getFunctionParts();
        for (int i = 0; i < functionParts.size(); i++) {
            gridPane.add(new Text(functionParts.get(i).toString()),0,i);
        }
        Scene scene = new Scene(gridPane);
        table.setScene(scene);
        table.show();
    }

    public void drawGrid(Integer density, Pane buttons) {
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < 700; i += density) {
            Line line1 = new Line(i, 0, i, 300);
            line1.setStroke(Color.LIGHTGRAY);
            lines.add(line1);
        }
        for (int i = 0; i < 350; i += density) {
            Line line2 = new Line(0, i, 700, i);
            line2.setStroke(Color.LIGHTGRAY);
            lines.add(line2);
        }
        buttons.getChildren().addAll(lines);
    }

    static class Pos {
        double x, y;
    }
}
