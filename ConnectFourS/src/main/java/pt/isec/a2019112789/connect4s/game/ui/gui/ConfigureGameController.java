package pt.isec.a2019112789.connect4s.game.ui.gui;

import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.isec.a2019112789.connect4s.game.logic.data.Computer;
import pt.isec.a2019112789.connect4s.game.logic.data.EDisc;
import pt.isec.a2019112789.connect4s.game.logic.data.Human;
import pt.isec.a2019112789.connect4s.game.logic.data.Player;

public class ConfigureGameController extends Controller {

    @FXML
    private FlowPane tilePlayers;
    @FXML
    private Button btnAddPlayer;
    @FXML
    private Button btnStartGame;
    @FXML
    private Button btnBack;

    private static final double WIDTH = 294;
    private static final double HEIGHT = 368;

    @FXML
    private void onBtnAddPlayer(ActionEvent event) {
        Dialog<Player> dialog = new Dialog<>();
        dialog.setTitle("Add a new player");
        ButtonType addBtn = new ButtonType("Add player", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(14);
        grid.setPadding(new Insets(14));
        TextField tfName = new TextField();
        tfName.setPromptText("Player name");
        ChoiceBox<String> discsCB = new ChoiceBox<>();
        for (EDisc d : EDisc.values()) {
            if (d != EDisc.Empty && d != EDisc.Special) {
                discsCB.getItems().add(d.name());
            }
        }
        CheckBox isBotCkB = new CheckBox("Bot");
        grid.add(new Label("Name:"), 0, 0);
        grid.add(tfName, 1, 0);
        grid.add(new Label("Disc:"), 0, 1);
        grid.add(discsCB, 1, 1);
        grid.add(isBotCkB, 2, 0);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter((p) -> {
            if (p == addBtn) {
                String name = tfName.getText();
                String disc = discsCB.getValue();
                boolean isBot = isBotCkB.isSelected();

                if (name != null && !name.isBlank() && disc != null && !disc.isBlank()) {
                    if (isBot) {
                        return new Computer(name, EDisc.valueOf(disc));
                    } else {
                        return new Human(name, EDisc.valueOf(disc));
                    }
                }
            }
            return null;
        });
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(app.getAppIcon());
        Optional<Player> optionalPlayer = dialog.showAndWait();

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            app.getStateMachine().addPlayer(player);
        }
    }

    @FXML
    private void onBtnStartGame(ActionEvent event) {
        app.getStateMachine().startGame();
    }

    @FXML
    private void onBtnBack(ActionEvent event) {
        app.getStateMachine().exit();
    }

    @Override
    public void show() {
        super.show();
        app.setStageSizes(WIDTH, HEIGHT);
        tilePlayers.getChildren().clear();
        List<Player> players = app.getStateMachine().getPlayers();
        players.forEach(p -> tilePlayers.getChildren().add(new PlayerNode(
                p.getName(),
                p.getDisc().name(),
                p.isBot() ? "Bot" : "Human", (t) -> {
            app.getStateMachine().removePlayer(p.getName());
        })));
    }

    @Override
    public void hide() {
        super.hide();
        tilePlayers.getChildren().clear();
    }
}
