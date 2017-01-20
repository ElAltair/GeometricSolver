package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Pane buttons = loader.load();
        Controller controller = loader.getController();
        drawGrid(50, buttons);
        Scene scene = new Scene(buttons, 700, 500);
        controller.init(buttons);


        //scene.addEventHandler(DragEvent.DRAG_ENTERED, e -> System.out.println("drag enter"));

        primaryStage.setTitle("Geometric Solver!");
        primaryStage.setScene(scene);
        primaryStage.show();
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


}