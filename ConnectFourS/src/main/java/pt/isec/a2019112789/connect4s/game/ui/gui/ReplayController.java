package pt.isec.a2019112789.connect4s.game.ui.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pt.isec.a2019112789.connect4s.game.logic.StateMachineObservable;
import pt.isec.a2019112789.connect4s.game.logic.data.EDisc;

public class ReplayController extends Controller {

    @FXML
    private Pane paneBoard;
    @FXML
    private Button btnStep;

    private Thread step;

    private static final double WIDTH = 370;
    private static final double HEIGHT = 383;

    @FXML
    private void onBtnStep(ActionEvent event) {
        app.getStateMachine().replayStep();
    }

    @Override
    public void show() {
        super.show();
        app.setStageSizes(WIDTH, HEIGHT);
        StateMachineObservable stateMachine = app.getStateMachine();

        if (step == null || !step.isAlive()) {
            step = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(() -> {
                    btnStep.fire();
                });
            });
            step.setDaemon(true);
            step.start();
        }

        EDisc[][] board = stateMachine.getBoard();
        int maxCols = stateMachine.getMaxCols();
        int maxRows = stateMachine.getMaxRows();
        paneBoard.getChildren().clear();
        GridPane gridBoard = new GridPane();
        gridBoard.getStyleClass().add("board");
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxCols; j++) {
                gridBoard.add(new DiscNode(board[i][j].name()), j, i);
            }
        }
        paneBoard.getChildren().add(gridBoard);
    }

    @Override
    public void hide() {
        super.hide();
        paneBoard.getChildren().clear();
    }
}
