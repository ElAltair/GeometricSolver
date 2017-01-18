package javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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


        //r.setOnMousePressed(onPress);
        //r.setOnMouseDragged(onDragged);
        // controller.initDraggingNodes(onPress, onDragged, onMouseMoved);
        Scene scene = new Scene(buttons, 600, 600);
        controller.init(buttons);
        Button btn = new Button("Press me");
        btn.setBackground(new Background(new BackgroundFill(
                Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        btn.setOnAction(event -> System.out.println("Hello World"));


//        drawGrid(10);

        buttons.getChildren().add(btn);

        scene.addEventHandler(DragEvent.DRAG_ENTERED, e -> System.out.println("drag enter"));

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


}