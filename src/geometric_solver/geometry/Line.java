package geometric_solver.geometry;

import geometric_solver.math.*;
import geometric_solver.math.constraints.FixLength;
import javafx.LineContextMenu;
import javafx.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
            Point point = (Point) event.getSource();
            point.updateLagrangeComponents(newPosX, newPosY);
            newtonSolver.solve();
            updateObjectOnScene();

            length = Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
            this.setStartX(p1.getX());
            this.setEndX(p2.getX());
            this.setStartY(p1.getY());
            this.setEndY(p2.getY());
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
            this.setStartX(this.getStartX() + offsetX);
            this.setEndX(this.getEndX() + offsetX);
            this.setStartY(this.getStartY() + offsetY);
            this.setEndY(this.getEndY() + offsetY);
            p1.setCenterX(this.getStartX());
            p1.setCenterY(this.getStartY());
            p2.setCenterX(this.getEndX());
            p2.setCenterY(this.getEndY());
            p1.updateLagrangeComponents(p1.getCenterX(), p1.getCenterY());
            p2.updateLagrangeComponents(p2.getCenterX(), p2.getCenterY());
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
            lineContextMenu.show(this, event.getScreenX(), event.getScreenY());
        });
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
            line.setStartX((source.getValue(p1.getSquaredSummX().getVariable())));
            line.setStartY((source.getValue(p1.getSquaredSummY().getVariable())));
            line.setEndX((source.getValue(p2.getSquaredSummX().getVariable())));
            line.setEndY((source.getValue(p2.getSquaredSummY().getVariable())));
            source.setVariable(p1.getSquaredSummX().getVariable(), p1.getX());
            source.setVariable(p1.getSquaredSummY().getVariable(), p1.getY());
            source.setVariable(p2.getSquaredSummX().getVariable(), p2.getX());
            source.setVariable(p2.getSquaredSummY().getVariable(), p2.getY());
        });
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
