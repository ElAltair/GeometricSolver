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

//                // rectangle temp lines
//                for (Line l : tempLineForRectangle)
//                    root.getChildren().remove(l);
//                tempLineForRectangle.clear();

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

        onPressedEvent = event -> {
            // r.setFill(Color.RED);
            Shape shape = (Shape) event.getSource();
            double nodeX = event.getX();
            double nodeY = event.getY();
            double deltaX = root.getScene().getX() - event.getSceneX();
            double deltaY = root.getScene().getY() - event.getSceneY();
            double rectPosX = 0.0;
            double rectPosY = 0.0;
//            if (shape instanceof Rectangle) {
//                rectPosX = event.getSceneX() - ((Rectangle) event.getSource()).getX();
//                rectPosY = event.getSceneY() - ((Rectangle) event.getSource()).getY();
//                System.out.println("RECTANGLE");
//
//            }
            System.out.println("Event: " + nodeX + " " + nodeY);
            System.out.println("Scene Event: " + event.getSceneX() + " " + event.getSceneY());
            System.out.println("Shape Event: " + ((Node) event.getSource()).getTranslateX() + " " + ((Node) event.getSource()).getTranslateY());
//            if (shape instanceof Rectangle)
//                System.out.println("Rect Event: " + rectPosX + " " + rectPosY);
            System.out.println("Shape Event: " + deltaX + " " + deltaY);
            System.out.println();
            Shape node = ((Shape) event.getSource());
//            if (node instanceof Rectangle) {
//                rectPos.x = rectPosX;
//                rectPos.y = rectPosY;
//
//                Rectangle rectangle = (Rectangle) node;
//                double leftTopX = rectangle.getX();
//                double leftTopY = rectangle.getY();
//                double width = rectangle.getWidth();
//                double height = rectangle.getHeight();
//
//                System.out.println("Width = " + width);
//                System.out.println("Height = " + height);
//                System.out.println("Start X = " + leftTopX + " Start Y = " + leftTopY);
//
//                // create 4 points around rectangle
//                Circle topLeft = new Circle(leftTopX, leftTopY, R);
//                Circle topRight = new Circle(leftTopX + width, leftTopY, R);
//                Circle bottomLeft = new Circle(leftTopX, leftTopY + height, R);
//                Circle bottomRight = new Circle(leftTopX + width, leftTopY + height, R);
//                boundPoints.add(topLeft);
//                boundPoints.add(topRight);
//                boundPoints.add(bottomLeft);
//                boundPoints.add(bottomRight);
//                for (Shape s : boundPoints)
//                    root.getChildren().add(s);
//
//            }
            //r.getScene().setCursor(Cursor.MOVE);
        };

        onDragEvent = event -> {
            double ofsetX = event.getSceneX();
            double ofsetY = event.getSceneY();
            //System.out.println("Rect: " + ((Rectangle) event.getSource()).getX() + " " + ((Rectangle) event.getSource()).getY());
            //System.out.println("Delta x = " + ofsetX + " Delta y = " + ofsetY);
            //double prevPosX = ((Rectangle)event.getSource()).getX();
            //double prevPosy = ((Rectangle)event.getSource()).getY();
//            if (event.getSource() instanceof Rectangle) {
//                Rectangle r = ((Rectangle) event.getSource());
//                if (rectPos.x > r.getWidth()) {
//                    r.setX(ofsetX + rectPos.x);
//                    r.setY(ofsetY + rectPos.y);
//                } else {
//                    r.setX(ofsetX - rectPos.x);
//                    r.setY(ofsetY - rectPos.y);
//                }
//            }
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

        onCircleDraggedEvent = event -> {
            double ofsetX = event.getSceneX();
            double ofsetY = event.getSceneY();
            double newPosX = ofsetX + oldPoint.x;
            double newPosY = ofsetY + oldPoint.y;
            Point point = (Point) event.getSource();
            point.updateLagrangeComponents(newPosX, newPosY);
            lagrangeLabel.setText(lagrange.print());
            newtonSolver.solve();
            updateObjectOnScene();
        };

        onCircleReleasedEvent = event -> {
        };


        onMouseMoved = event -> {
            sceneXCoord.setText(((Double) event.getX()).toString());
            sceneYCoord.setText(((Double) event.getY()).toString());
        };

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

    public void initDraggingNodes(EventHandler<MouseEvent> mouse, EventHandler<MouseEvent> drag,
                                  EventHandler<MouseEvent> mouseMoved) {
        onPressedEvent = mouse;
        onDragEvent = drag;
        onMouseMoved = mouseMoved;
    }

//    public void createRect(ActionEvent actionEvent) {
//        catchMouseCoordinates = true;
//        root.getScene().setCursor(Cursor.CROSSHAIR);
//        updateMouseClickedHandler(DrawingObjects.RECTANGLE);
//    }

    public void createLine(ActionEvent actionEvent) {
        catchMouseCoordinates = true;
        root.getScene().setCursor(Cursor.CROSSHAIR);
        updateMouseClickedHandler(DrawingObjects.LINE);

        /*
        Line l = new Line(100.0,100.0,120.0,600.0);
        l.setStroke(Color.GREEN);
        l.setOnMousePressed(onPressedEvent);
        l.setOnMouseDragged(onDragEvent);
        root.getChildren().add(l);
        */
    }

    public void createPoint(ActionEvent actionEvent) {
        catchMouseCoordinates = true;
        root.getScene().setCursor(Cursor.CROSSHAIR);
        updateMouseClickedHandler(DrawingObjects.POINT);
        /*
        scene.setOnMouseMoved((e) -> {
            controller.sceneXCoord.setText(((Double) e.getX()).toString());
            controller.sceneYCoord.setText(((Double) e.getY()).toString());
        });
        */
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
                    point.onMouseRelease(onCircleReleasedEvent);
                    point.onMouseDraged(onCircleDraggedEvent);
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

                        //TODO UNCOMMENT AFTER LINE CHANGES
                         line.setStroke(Color.GREEN);


//                        line.setOnMouseEntered(((event) -> {
//                            Line tLine = (Line) event.getSource();
//                            tLine.setStroke(Color.BLUE);
//                        }));
//                        line.setOnMouseExited((event) -> {
//                            Line tLine = (Line) event.getSource();
//                            tLine.setStroke(Color.GREEN);
//                        });
//                        createdPoint.get(0).setOnMouseEntered(((event) -> {
//                            Circle circle = (Circle) event.getSource();
//                            circle.setStroke(Color.RED);
//                        }));
//                        createdPoint.get(0).setOnMouseExited((event) -> {
//                            Circle circle = (Circle) event.getSource();
//                            circle.setStroke(Color.GREEN);
//                        });
//                        createdPoint.get(1).setOnMouseEntered(((event) -> {
//                            Circle circle = (Circle) event.getSource();
//                            circle.setStroke(Color.RED);
//                        }));
//                        createdPoint.get(1).setOnMouseExited((event) -> {
//                            Circle circle = (Circle) event.getSource();
//                            circle.setStroke(Color.GREEN);
//                        });
//
//                        Pos oldPoint = new Pos();
//                        createdPoint.get(0).setOnMouseClicked((event) -> {
//                            // r.setFill(Color.RED);
//                            Circle source = (Circle) event.getSource();
//                            double nodeX = event.getX();
//                            double nodeY = event.getY();
//                            double deltaX = root.getScene().getX() - event.getSceneX();
//                            double deltaY = root.getScene().getY() - event.getSceneY();
//                            double pointPosX = 0.0;
//                            double pointPosY = 0.0;
//
//                            pointPosX = event.getSceneX() - ((Circle) event.getSource()).getCenterX();
//                            pointPosY = event.getSceneY() - ((Circle) event.getSource()).getCenterY();
//                            System.out.println("POINT");
//
//                            oldPoint.x = pointPosX;
//                            oldPoint.y = pointPosY;
//
//                        });
//                        createdPoint.get(1).setOnMouseClicked((event -> {
//                            // r.setFill(Color.RED);
//                            Circle source = (Circle) event.getSource();
//                            double nodeX = event.getX();
//                            double nodeY = event.getY();
//                            double deltaX = root.getScene().getX() - event.getSceneX();
//                            double deltaY = root.getScene().getY() - event.getSceneY();
//                            double pointPosX = 0.0;
//                            double pointPosY = 0.0;
//
//                            pointPosX = event.getSceneX() - ((Circle) event.getSource()).getCenterX();
//                            pointPosY = event.getSceneY() - ((Circle) event.getSource()).getCenterY();
//                            System.out.println("POINT");
//
//                            oldPoint.x = pointPosX;
//                            oldPoint.y = pointPosY;
//                        }));
//                        createdPoint.get(0).setOnMouseDragged((event) -> {
//                            double ofsetX = event.getSceneX();
//                            double ofsetY = event.getSceneY();
//                            Circle c = ((Circle) event.getSource());
//                            c.setCenterX(ofsetX + oldPoint.x);
//                            c.setCenterY(ofsetY + oldPoint.y);
//                            line.setStartX(ofsetX + oldPoint.x);
//                            line.setStartY(ofsetY + oldPoint.y);
//
//                        });
//                        createdPoint.get(1).setOnMouseDragged((event -> {
//                            double ofsetX = event.getSceneX();
//                            double ofsetY = event.getSceneY();
//                            Circle c = ((Circle) event.getSource());
//                            c.setCenterX(ofsetX + oldPoint.x);
//                            c.setCenterY(ofsetY + oldPoint.y);
//                            line.setEndX(ofsetX + oldPoint.x);
//                            line.setEndY(ofsetY + oldPoint.y);
//
//                        }));
//                        */

                        //lagrange.addComponents(line.getLagrangeComponents());

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

//            else if (type == DrawingObjects.RECTANGLE) {
//
//                root.getScene().setOnMouseClicked((clickedEvent) -> {
//                    double startX = clickedEvent.getSceneX();
//                    double startY = clickedEvent.getSceneY();
//                    Circle point = new Circle(startX, startY, R);
//                    createdPoint.add(point);
//                    root.getChildren().add(point);
//
//                    root.getScene().setOnMouseMoved((moveEvent) -> {
//
//                        double xMousePoss = moveEvent.getSceneX();
//                        double yMousePoss = moveEvent.getSceneY();
//                        double tempWidth = xMousePoss - startX;
//                        double tempHeight = yMousePoss - startY;
//                        for (Line line : tempLineForRectangle) {
//                            root.getChildren().remove(line);
//                        }
//                        tempLineForRectangle.clear();
//
//                        boolean fromLeftToRight = true;
//                        boolean fromTopToBottom = true;
//                        if (xMousePoss < startX)
//                            fromLeftToRight = false;
//                        if (yMousePoss < startY)
//                            fromTopToBottom = false;
//
//                        if (fromLeftToRight) {
//                            // left -> right
//                            // top line
//                            Line top = new Line(startX, startY, xMousePoss, startY);
//                            top.getStrokeDashArray().add(strokeSize);
//                            tempLineForRectangle.add(top);
//
//                            // bottom line
//                            Line bottom = new Line(startX, yMousePoss, xMousePoss, yMousePoss);
//                            bottom.getStrokeDashArray().add(strokeSize);
//                            tempLineForRectangle.add(bottom);
//                        } else {
//                            // right -> left
//                            // top line
//                            Line top = new Line(xMousePoss, startY, startX, startY);
//                            top.getStrokeDashArray().add(strokeSize);
//                            tempLineForRectangle.add(top);
//
//                            // bottom line
//                            Line bottom = new Line(xMousePoss, yMousePoss, startX, yMousePoss);
//                            bottom.getStrokeDashArray().add(strokeSize);
//                            tempLineForRectangle.add(bottom);
//                        }
//
//                        if (fromTopToBottom) {
//                            // top -> bottom
//                            // left line
//                            Line left = new Line(startX, startY, startX, yMousePoss);
//                            left.getStrokeDashArray().add(strokeSize);
//                            tempLineForRectangle.add(left);
//
//                            // right line
//                            Line right = new Line(xMousePoss, startY, xMousePoss, yMousePoss);
//                            right.getStrokeDashArray().add(strokeSize);
//                            tempLineForRectangle.add(right);
//                        } else {
//                            // bottom -> top
//                            // left line
//                            Line left = new Line(startX, yMousePoss, startX, startY);
//                            left.getStrokeDashArray().add(strokeSize);
//                            tempLineForRectangle.add(left);
//
//                            // right line
//                            Line right = new Line(xMousePoss, yMousePoss, xMousePoss, startY);
//                            right.getStrokeDashArray().add(strokeSize);
//                            tempLineForRectangle.add(right);
//                        }
//
//
//                        for (Line line : tempLineForRectangle)
//                            root.getChildren().add(line);
//
//                    });
//                    if (createdPoint.size() == 2) {
//                        double xMousePoss = ((Circle) createdPoint.get(1)).getCenterX();
//                        double yMousePoss = ((Circle) createdPoint.get(1)).getCenterY();
//                        double xStartPoss = ((Circle) createdPoint.get(0)).getCenterX();
//                        double yStartPoss = ((Circle) createdPoint.get(0)).getCenterY();
//
//                        double startRectanglePosX;
//                        double startRectanglePosY;
//
//                        double width = Math.abs(xMousePoss - xStartPoss);
//                        double height = Math.abs(yMousePoss - yStartPoss);
//
//                        if (xMousePoss < xStartPoss) {
//                            startRectanglePosX = xStartPoss - width;
//                        } else {
//                            startRectanglePosX = xStartPoss;
//                        }
//
//                        if (yMousePoss < yStartPoss) {
//                            startRectanglePosY = yStartPoss - height;
//                        } else {
//                            startRectanglePosY = yStartPoss;
//
//                        }
//
//                        Rectangle r = new Rectangle(startRectanglePosX, startRectanglePosY, width, height);
//                        r.setFill(Color.RED);
//                        r.setOnMousePressed(onPressedEvent);
//                        r.setOnMouseDragged(onDragEvent);
//                        root.getChildren().add(r);
//                        for (Shape c : createdPoint)
//                            root.getChildren().remove(c);
//
//                        for (Line line : tempLineForRectangle)
//                            root.getChildren().remove(line);
//
//                        createdPoint.clear();
//                        tempLineForRectangle.clear();
//
//                        root.getScene().setOnMouseMoved(null);
//                    }
//                });
//
//            }*/
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
