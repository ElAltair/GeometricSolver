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
        Pane root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root, 700, 500);
        controller.init(root);


        //scene.addEventHandler(DragEvent.DRAG_ENTERED, e -> System.out.println("drag enter"));

        primaryStage.setTitle("Geometric Solver!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}