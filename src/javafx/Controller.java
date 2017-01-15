package javafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Controller {

    public Button rectButton;
    public Button pointButton;
    public Button lineButton;
    public Label sceneXCoord;
    public Label sceneYCoord;
    private Pane root;
    private EventHandler<MouseEvent> onDragEvent;
    private EventHandler<MouseEvent> onPressedEvent;
    private EventHandler<MouseEvent> onMouseMoved;
    private boolean catchMouseCoordinates;


    private ArrayList<Shape> createdPoint;
    private Line tempLineForLineVisualization;
    private ArrayList<Line> tempLineForRectangle;

    public void init(Pane pane) {
        root = pane;
        catchMouseCoordinates = false;
        createdPoint = new ArrayList<>();
        tempLineForRectangle = new ArrayList<>();

        root.getScene().setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                catchMouseCoordinates = false;
                root.getScene().setCursor(Cursor.DEFAULT);
                updateMouseClickedHandler(DrawingObjects.NONE);
                root.getChildren().remove(tempLineForLineVisualization);
                for (Line l : tempLineForRectangle)
                    root.getChildren().remove(l);

                for (Shape s : createdPoint)
                    root.getChildren().remove(s);

                tempLineForRectangle.clear();
                tempLineForLineVisualization = null;
                createdPoint.clear();
            }
        });


    }

    public void initDraggingNodes(EventHandler<MouseEvent> mouse, EventHandler<MouseEvent> drag,
                                  EventHandler<MouseEvent> mouseMoved) {
        onPressedEvent = mouse;
        onDragEvent = drag;
        onMouseMoved = mouseMoved;
    }

    public void createRect(ActionEvent actionEvent) {

        catchMouseCoordinates = true;
        root.getScene().setCursor(Cursor.CROSSHAIR);
        updateMouseClickedHandler(DrawingObjects.RECTANGLE);


    }

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
        final double strokeSize = 4.0;
        if (catchMouseCoordinates) {
            if (type == DrawingObjects.POINT) {
                root.getScene().setOnMouseClicked((e) -> {
                    double xClick = e.getSceneX();
                    double yClick = e.getSceneY();
                    Circle point = new Circle(xClick, yClick, 2.0);
                    root.getChildren().add(point);
                });
            } else if (type == DrawingObjects.LINE) {
                root.getScene().setOnMouseClicked((clickedEvent) -> {
                    double xClick = clickedEvent.getSceneX();
                    double yClick = clickedEvent.getSceneY();
                    Circle point = new Circle(xClick, yClick, 2.0);
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
                        Line line = new Line(((Circle) createdPoint.get(0)).getCenterX(), ((Circle) createdPoint.get(0)).getCenterY(), ((Circle) createdPoint.get(1)).getCenterX(), ((Circle) createdPoint.get(1)).getCenterY());
                        line.setStroke(Color.GREEN);
                        root.getChildren().add(line);
                        createdPoint.clear();
                        root.getScene().setOnMouseMoved(null);
                    }
                });
            } else if (type == DrawingObjects.RECTANGLE) {
                root.getScene().setOnMouseClicked((clickedEvent) -> {
                    double startX = clickedEvent.getSceneX();
                    double startY = clickedEvent.getSceneY();
                    Circle point = new Circle(startX, startY, 2.0);
                    createdPoint.add(point);
                    root.getChildren().add(point);

                    root.getScene().setOnMouseMoved((moveEvent) -> {

                        double xMousePoss = moveEvent.getSceneX();
                        double yMousePoss = moveEvent.getSceneY();
                        double tempWidth = xMousePoss - startX;
                        double tempHeight = yMousePoss - startY;
                        for (Line line : tempLineForRectangle) {
                            root.getChildren().remove(line);
                        }
                        tempLineForRectangle.clear();

                        boolean fromLeftToRight = true;
                        boolean fromTopToBottom = true;
                        if (xMousePoss < startX)
                            fromLeftToRight = false;
                        if (yMousePoss < startY)
                            fromTopToBottom = false;

                        if (fromLeftToRight) {
                            // left -> right
                            // top line
                            Line top = new Line(startX, startY, xMousePoss, startY);
                            top.getStrokeDashArray().add(strokeSize);
                            tempLineForRectangle.add(top);

                            // bottom line
                            Line bottom = new Line(startX, yMousePoss, xMousePoss, yMousePoss);
                            bottom.getStrokeDashArray().add(strokeSize);
                            tempLineForRectangle.add(bottom);
                        } else {
                            // right -> left
                            // top line
                            Line top = new Line(xMousePoss, startY, startX, startY);
                            top.getStrokeDashArray().add(strokeSize);
                            tempLineForRectangle.add(top);

                            // bottom line
                            Line bottom = new Line(xMousePoss, yMousePoss, startX, yMousePoss);
                            bottom.getStrokeDashArray().add(strokeSize);
                            tempLineForRectangle.add(bottom);
                        }

                        if (fromTopToBottom) {
                            // top -> bottom
                            // left line
                            Line left = new Line(startX, startY, startX, yMousePoss);
                            left.getStrokeDashArray().add(strokeSize);
                            tempLineForRectangle.add(left);

                            // right line
                            Line right = new Line(xMousePoss, startY, xMousePoss, yMousePoss);
                            right.getStrokeDashArray().add(strokeSize);
                            tempLineForRectangle.add(right);
                        } else {
                            // bottom -> top
                            // left line
                            Line left = new Line(startX, yMousePoss, startX, startY);
                            left.getStrokeDashArray().add(strokeSize);
                            tempLineForRectangle.add(left);

                            // right line
                            Line right = new Line(xMousePoss, yMousePoss, xMousePoss, startY);
                            right.getStrokeDashArray().add(strokeSize);
                            tempLineForRectangle.add(right);
                        }


                        for (Line line : tempLineForRectangle)
                            root.getChildren().add(line);

                    });
                    if (createdPoint.size() == 2) {
                        double xMousePoss = ((Circle) createdPoint.get(1)).getCenterX();
                        double yMousePoss = ((Circle) createdPoint.get(1)).getCenterY();
                        double xStartPoss = ((Circle) createdPoint.get(0)).getCenterX();
                        double yStartPoss = ((Circle) createdPoint.get(0)).getCenterY();

                        double startRectanglePosX;
                        double startRectanglePosY;

                        double width = Math.abs(xMousePoss - xStartPoss);
                        double height = Math.abs(yMousePoss - yStartPoss);

                        if (xMousePoss < xStartPoss) {
                            startRectanglePosX = xStartPoss - width;
                        } else {
                            startRectanglePosX = xStartPoss;
                        }

                        if (yMousePoss < yStartPoss) {
                            startRectanglePosY = yStartPoss - height;
                        } else {
                            startRectanglePosY = yStartPoss;

                        }

                        Rectangle r = new Rectangle(startRectanglePosX, startRectanglePosY, width, height);
                        r.setFill(Color.RED);
                        r.setOnMousePressed(onPressedEvent);
                        r.setOnMouseDragged(onDragEvent);
                        root.getChildren().add(r);
                        for (Shape c : createdPoint)
                            root.getChildren().remove(c);

                        for (Line line : tempLineForRectangle)
                            root.getChildren().remove(line);

                        createdPoint.clear();
                        tempLineForRectangle.clear();

                        root.getScene().setOnMouseMoved(null);
                    }
                });

            }
        } else {
            root.getScene().setOnMouseClicked(null);
            root.getScene().setOnMouseMoved(onMouseMoved);
        }

    }
}
