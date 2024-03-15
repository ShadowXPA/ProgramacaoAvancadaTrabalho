package pt.isec.a2019112789.connect4s.game.ui.gui;

import javafx.scene.layout.Pane;

public class DiscNode extends Pane {

    public DiscNode(String color) {
        getStyleClass().clear();
        getStyleClass().add("piece");
        getStyleClass().add(color.toLowerCase());
    }
}
