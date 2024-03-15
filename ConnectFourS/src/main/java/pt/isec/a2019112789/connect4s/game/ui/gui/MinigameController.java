package pt.isec.a2019112789.connect4s.game.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MinigameController extends Controller {

    @FXML
    private Button btnConnect4;
    @FXML
    private Button btnMinigame;
    @FXML
    private Label lName;

    private static final double WIDTH = 415;
    private static final double HEIGHT = 173;

    @FXML
    private void onBtnConnect4(ActionEvent event) {
        app.getStateMachine().playConnect4();
    }

    @FXML
    private void onBtnMinigame(ActionEvent event) {
        app.getStateMachine().playMinigame();
    }

    @Override
    public void show() {
        super.show();
        app.setStageSizes(WIDTH, HEIGHT);
        lName.setText(app.getStateMachine().getCurrentPlayerName());
    }
}
