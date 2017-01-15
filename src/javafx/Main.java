package javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Pane buttons = loader.load();
        Controller controller = loader.getController();


        //root = new Group();
        // Rectangle r = new Rectangle(0.0, 0.0, 100.0, 100.0);
        //r.setFill(null);
        // r.setStroke(Color.BLUE);
        Pos prevPos = new Pos();
        Pos rectPos = new Pos();
        EventHandler<MouseEvent> onPress = event -> {
            // r.setFill(Color.RED);
            Shape shape = (Shape) event.getSource();
            double nodeX = event.getX();
            double nodeY = event.getY();
            double deltaX = primaryStage.getX() - event.getSceneX();
            double deltaY = primaryStage.getY() - event.getSceneY();
            double rectPosX = 0.0;
            double rectPosY = 0.0;
            if (shape instanceof Rectangle) {
                rectPosX = event.getSceneX() - ((Rectangle) event.getSource()).getX();
                rectPosY = event.getSceneY() - ((Rectangle) event.getSource()).getY();

            }
            System.out.println("Event: " + nodeX + " " + nodeY);
            System.out.println("Scene Event: " + event.getSceneX() + " " + event.getSceneY());
            System.out.println("Shape Event: " + ((Node) event.getSource()).getTranslateX() + " " + ((Node) event.getSource()).getTranslateY());
            if (shape instanceof Rectangle)
                System.out.println("Rect Event: " + rectPosX + " " + rectPosY);
            System.out.println("Shape Event: " + deltaX + " " + deltaY);
            System.out.println();
            Shape node = ((Shape) event.getSource());
            if (node instanceof Rectangle) {
                rectPos.x = rectPosX;
                rectPos.y = rectPosY;
            }
            //r.getScene().setCursor(Cursor.MOVE);
        };
        EventHandler<MouseEvent> onDragged = event -> {
            double ofsetX = event.getSceneX();
            double ofsetY = event.getSceneY();
            //System.out.println("Rect: " + ((Rectangle) event.getSource()).getX() + " " + ((Rectangle) event.getSource()).getY());
            //System.out.println("Delta x = " + ofsetX + " Delta y = " + ofsetY);
            //double prevPosX = ((Rectangle)event.getSource()).getX();
            //double prevPosy = ((Rectangle)event.getSource()).getY();
            if (event.getSource() instanceof Rectangle) {
                Rectangle r = ((Rectangle) event.getSource());
                if (rectPos.x > r.getWidth()) {
                    r.setX(ofsetX + rectPos.x);
                    r.setY(ofsetY + rectPos.y);
                } else {
                    r.setX(ofsetX - rectPos.x);
                    r.setY(ofsetY - rectPos.y);
                }
            }
        };

        EventHandler<MouseEvent> onMouseMoved = event -> {
            controller.sceneXCoord.setText(((Double) event.getX()).toString());
            controller.sceneYCoord.setText(((Double) event.getY()).toString());
        };

        //r.setOnMousePressed(onPress);
        //r.setOnMouseDragged(onDragged);
        controller.initDraggingNodes(onPress, onDragged, onMouseMoved);
        Scene scene = new Scene(buttons, 600, 600);
        controller.init(buttons);
        Button btn = new Button("Press me");
        btn.setOnAction(event -> System.out.println("Hello World"));


//        drawGrid(10);

        buttons.getChildren().add(btn);

        scene.addEventHandler(DragEvent.DRAG_ENTERED, e -> System.out.println("drag enter"));

        scene.setOnMouseMoved(onMouseMoved);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void drawGrid(Integer density) {
        for (int i = 0; i < 600; i += density) {
            Line line1 = new Line(i, 0, i, 600);
            line1.setStroke(Color.LIGHTGRAY);
            Line line2 = new Line(0, i, 600, i);
            line2.setStroke(Color.LIGHTGRAY);
            // .getChildren().addAll(line1, line2);
        }
    }

    static class Pos {
        double x, y;
    }

}