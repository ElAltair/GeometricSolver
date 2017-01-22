package javafx;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Laiserg on 21.01.2017.
 */
public class PointContextMenu extends ContextMenu {

    public HashMap<String, MenuItem> menuItems = new HashMap<>();

    public PointContextMenu() {
        super();
        initMenuItems();
    }

    public void initMenuItems() {
        MenuItem fixFull = new MenuItem("Fix point at this point (:");
        MenuItem fixAxis = new MenuItem("Fix point to axis");
        MenuItem fixDistanceToPoint = new MenuItem("Fix distance to point");
        MenuItem showConstrains = new MenuItem("Show constraints...");
        menuItems.put("fixAxis", fixAxis);
        menuItems.put("fixFull", fixFull);
        menuItems.put("fixDistanceToPoint", fixDistanceToPoint);
        menuItems.put("showConstraints", showConstrains);
        this.getItems().addAll(fixAxis, fixFull, fixDistanceToPoint, showConstrains);
    }


}
