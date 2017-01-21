package javafx;

import geometric_solver.geometry.Point;
import geometric_solver.math.Lagrange;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Created by Laiserg on 21.01.2017.
 */
public class PointContextMenu extends ContextMenu {


    PointContextMenu() {
        super();

        initMenuItems();
    }

    public void initMenuItems() {
        MenuItem fixFull = new MenuItem("Fix point at this point (:");
        MenuItem fixAxis = new MenuItem("Fix point to axis");
        MenuItem fixDistanceToPoint = new MenuItem("Fix distance to point");

        fixFull.setOnAction(event -> {
            System.out.println("Fix full!");
        });
        fixAxis.setOnAction(event -> {
        });
        fixAxis.setOnAction(event -> {
            System.out.println("Fix axis!");
        });
        this.getItems().addAll(fixAxis, fixFull, fixDistanceToPoint);
    }


}
