package pt.isec.a2019112789.connect4s.game.ui.gui;

import java.io.File;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pt.isec.a2019112789.connect4s.game.logic.data.EDisc;
import pt.isec.a2019112789.connect4s.game.logic.StateMachineObservable;

public class PlayerTurnController extends Controller {

    @FXML
    private Pane paneBoard;
    @FXML
    private Label lName;
    @FXML
    private Label lDisc;
    @FXML
    private Label lBot;
    @FXML
    private Label lCredits;
    @FXML
    private Label lSpecialPiece;
    @FXML
    private Label lMinigameCounter;
    @FXML
    private TextField tfNumber;
    @FXML
    private Button btnPlace;
    @FXML
    private Button btnPlaceSpecial;
    @FXML
    private Button btnRollback;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnBack;

    private static final double WIDTH = 752;
    private static final double HEIGHT = 672;

    @FXML
    private void onBtnPlace(ActionEvent event) {
        String text = tfNumber.getText();
        int column = Integer.parseInt(text);
        app.getStateMachine().placeDisc(column);
    }

    @FXML
    private void onBtnPlaceSpecial(ActionEvent event) {
        String text = tfNumber.getText();
        int column = Integer.parseInt(text);
        app.getStateMachine().placeSpecialDisc(column);
    }

    @FXML
    private void onBtnRollback(ActionEvent event) {
        String text = tfNumber.getText();
        int numRollbacks = Integer.parseInt(text);
        app.getStateMachine().rollback(numRollbacks);
    }

    @FXML
    private void onBtnSave(ActionEvent event) {
        File saveFile = app.fileChooserPASave(".", "Save a game");
        if (saveFile != null) {
            app.getStateMachine().save(saveFile.getPath());
        }
    }

    @FXML
    private void onBtnBack(ActionEvent event) {
        app.getStateMachine().exit();
    }

    @Override
    public void show() {
        super.show();
        app.setStageSizes(WIDTH, HEIGHT);
        StateMachineObservable stateMachine = app.getStateMachine();
        EDisc[][] board = stateMachine.getBoard();
        int maxCols = stateMachine.getMaxCols();
        int maxRows = stateMachine.getMaxRows();
        boolean isBot = stateMachine.isCurrentPlayerBot();
        String playerName = stateMachine.getCurrentPlayerName();
        String playerDisc = stateMachine.getCurrentPlayerDisc().name();
        String playerBot = isBot ? "Bot" : "Human";
        String playerCredits = String.valueOf(stateMachine.getCurrentPlayerCredits());
        String playerSpecialPieces = String.valueOf(stateMachine.getCurrentPlayerSpecialPiece());
        String playerCounter = String.valueOf(stateMachine.getCurrentPlayerMinigameCounter());
        lName.setText(playerName);
        lDisc.setText(playerDisc);
        lBot.setText(playerBot);
        lCredits.setText(playerCredits);
        lSpecialPiece.setText(playerSpecialPieces);
        lMinigameCounter.setText(playerCounter);
        tfNumber.setText("");
        tfNumber.requestFocus();

        paneBoard.getChildren().clear();
        GridPane gridBoard = new GridPane();
        gridBoard.getStyleClass().add("board");
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxCols; j++) {
                gridBoard.add(new DiscNode(board[i][j].name()), j, i);
            }
        }
        gridBoard.setOnMouseClicked(eh -> {
            eh.consume();
            Node node = (Node) eh.getTarget();
            Integer column = GridPane.getColumnIndex(node);
            if (column != null) {
                Platform.runLater(() -> {
                    tfNumber.setText(String.valueOf(column + 1));
                    MouseButton button = eh.getButton();
                    if (button.equals(MouseButton.PRIMARY)) {
                        btnPlace.fire();
                    } else if (button.equals(MouseButton.SECONDARY)) {
                        btnPlaceSpecial.fire();
                    }
                });
            }
        });
        paneBoard.getChildren().add(gridBoard);

        if (isBot) {
            Platform.runLater(() -> {
                int column = stateMachine.getCurrentPlayerNextMove();
                tfNumber.setText(String.valueOf(column));
                btnPlace.fire();
            });
        }
    }

    @Override
    public void hide() {
        super.hide();
        tfNumber.setText("");
        paneBoard.getChildren().clear();
    }
}
