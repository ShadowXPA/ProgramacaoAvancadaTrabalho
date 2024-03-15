package pt.isec.a2019112789.connect4s.game.ui.gui;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController extends Controller {

    @FXML
    private Button btnPlay;
    @FXML
    private Button btnLoad;
    @FXML
    private Button btnReplay;
    @FXML
    private Button btnExit;

    private static final double WIDTH = 286;
    private static final double HEIGHT = 303;

    @FXML
    private void onBtnPlay(ActionEvent event) {
        app.getStateMachine().play();
    }

    @FXML
    private void onBtnLoad(ActionEvent event) {
        File loadFile = app.fileChooserPAOpen(".", "Load a game");
        if (loadFile != null) {
            app.getStateMachine().load(loadFile.getPath());
        }
    }

    @FXML
    private void onBtnReplay(ActionEvent event) {
        File replayFile = app.fileChooserPAOpen("Replays", "Replay a game");
        if (replayFile != null) {
            app.getStateMachine().replay(replayFile.getPath());
        }
    }

    @FXML
    private void onBtnExit(ActionEvent event) {
        app.getStateMachine().exit();
    }

    @Override
    public void show() {
        super.show();
        app.setStageSizes(WIDTH, HEIGHT);
    }
}
