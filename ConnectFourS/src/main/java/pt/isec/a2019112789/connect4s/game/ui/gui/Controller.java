package pt.isec.a2019112789.connect4s.game.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class Controller implements IController {

    protected Connect4App app;
    protected Pane root;

    @FXML
    public void initialize() {
        app = Connect4App.getInstance();
        root = null;
    }

    @Override
    public Parent getRoot() {
        return this.root;
    }

    @Override
    public final void setRoot(Parent root) {
        if (this.root == null) {
            this.root = (Pane) root;
        }
    }

    @Override
    public void show() {
        this.root.setVisible(true);
    }

    @Override
    public void hide() {
        this.root.setVisible(false);
    }
}
