package pt.isec.a2019112789.connect4s.game.ui.gui;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.isec.a2019112789.connect4s.game.logic.StateMachineObservable;
import pt.isec.a2019112789.connect4s.game.logic.data.EDisc;

public class GameOverController extends Controller {

    @FXML
    private Label lWinner;
    @FXML
    private VBox vBoxWinner;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnPlayAgain;
    @FXML
    private Button btnBack;
    @FXML
    private Pane paneBoard;

    private static final double WIDTH = 370;
    private static final double HEIGHT = 600;

    @FXML
    private void onBtnSave(ActionEvent event) {
        File saveFile = app.fileChooserPASave(".", "Save a game");
        if (saveFile != null) {
            app.getStateMachine().save(saveFile.getPath());
        }
    }

    @FXML
    private void onBtnPlayAgain(ActionEvent event) {
        app.getStateMachine().playAgain();
    }

    @FXML
    private void onBtnBack(ActionEvent event) {
        app.getStateMachine().exit();
    }

    @Override
    public void show() {
        super.show();
        app.setStageSizes(WIDTH, HEIGHT);
        vBoxWinner.getChildren().clear();
        StateMachineObservable stateMachine = app.getStateMachine();
        EDisc winnerDisc = stateMachine.getWinnerDisc();
        if (winnerDisc == EDisc.Empty) {
            lWinner.setText("It seems the game ended in a tie!");
        } else {
            lWinner.setText("Winner:");
            vBoxWinner.getChildren().add(new PlayerNode(
                    stateMachine.getWinnerName(),
                    stateMachine.getWinnerDisc().name(),
                    stateMachine.isWinnerBot() ? "Bot" : "Human"));
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
        vBoxWinner.getChildren().clear();
        paneBoard.getChildren().clear();
    }
}
