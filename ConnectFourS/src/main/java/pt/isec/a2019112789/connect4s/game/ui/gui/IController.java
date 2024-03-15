package pt.isec.a2019112789.connect4s.game.ui.gui;

import javafx.scene.Parent;

public interface IController {

    public Parent getRoot();

    public void setRoot(Parent root);

    public void show();

    public void hide();
}
