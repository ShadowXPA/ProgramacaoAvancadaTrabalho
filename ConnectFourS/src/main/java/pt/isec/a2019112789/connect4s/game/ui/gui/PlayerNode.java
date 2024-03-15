package pt.isec.a2019112789.connect4s.game.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlayerNode extends VBox {

    private final Label name;
    private final Label disc;
    private final Label bot;
    private Button remove;

    public PlayerNode(String name, String disc, String bot) {
        this(name, disc, bot, null);
    }

    public PlayerNode(String name, String disc, String bot, EventHandler<ActionEvent> eh) {
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #FFFFFF;"
                + "-fx-background-radius: 14;"
                + "-fx-spacing: 14;"
                + "-fx-padding: 14;"
                + "-fx-alignment: center;");
        this.name = new Label(name);
        this.disc = new Label(disc);
        this.bot = new Label(bot);
        this.name.setStyle("-fx-text-fill: #000000;");
        this.disc.setStyle("-fx-text-fill: #000000;");
        this.bot.setStyle("-fx-text-fill: #000000;");
        getChildren().addAll(this.name, this.disc, this.bot);
        if (eh != null) {
            this.remove = new Button("Remove");
            this.remove.setOnAction(eh);
            this.remove.getStyleClass().add("btn");
            getChildren().add(this.remove);
        }
    }
}
