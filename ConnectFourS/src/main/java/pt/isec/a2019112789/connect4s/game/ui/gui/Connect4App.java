package pt.isec.a2019112789.connect4s.game.ui.gui;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.isec.a2019112789.connect4s.game.logic.EState;
import pt.isec.a2019112789.connect4s.game.logic.StateMachine;
import pt.isec.a2019112789.connect4s.game.logic.StateMachineObservable;

public class Connect4App extends Application {

    private Stage stage;
    private Scene scene;
    private StateMachineObservable stateMachine;
    private HashMap<Class<?>, IController> views;
    private Image icon;

    private static Connect4App instance = null;
    public static final String APP_NAME = "Connect 4S";
    private static double widthDiff;
    private static double heightDiff;

    public StateMachineObservable getStateMachine() {
        return stateMachine;
    }

    @Override
    public void init() throws Exception {
        super.init();
        instance = this;
        stateMachine = new StateMachineObservable(new StateMachine());
        views = new HashMap<>();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Logs stage
        ListView<String> lv = new ListView<>();
        Pane Pane = new Pane(lv);
        Scene logsScene = new Scene(Pane);
        Stage logs = new Stage();
        logs.setScene(logsScene);
        logs.setTitle(APP_NAME + " Logs");
        logs.setResizable(false);
        logs.setOnCloseRequest(eh -> eh.consume());

        // Main stage
        StackPane root = new StackPane();
        scene = new Scene(root);
        this.stage = stage;
        this.stage.setScene(scene);
        this.stage.setTitle(APP_NAME);
        this.stage.setResizable(false);
        this.stage.setOnCloseRequest(eh -> {
            eh.consume();
            Platform.exit();
        });

        // Initialize state machine and listeners
        initStateHandlers();
        views.forEach((v, c) -> {
            c.hide();
            root.getChildren().add(c.getRoot());
        });
        stateMachine.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            String log = stateMachine.getMessageLogAndClearLog();
            if (!log.isBlank()) {
                ObservableList<String> items = lv.getItems();
                int size = items.size();
                items.add(log);
                lv.scrollTo(size);
            }
            updateView(evt);
        });

        // Add icons
        icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        this.stage.getIcons().add(icon);
        logs.getIcons().add(icon);

        // Show stages
        this.stage.show();
        this.stage.centerOnScreen();
        logs.show();
        logs.setX(this.stage.getX() + this.stage.getWidth());
        widthDiff = Math.abs(this.stage.getWidth() - scene.getWidth());
        heightDiff = Math.abs(this.stage.getHeight() - scene.getHeight());
        views.get(EState.MainMenu.getStateClass()).show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public void setStageSizes(double width, double height) {
        double realWidth = width + widthDiff;
        double realHeight = height + heightDiff;
        stage.setWidth(realWidth);
        stage.setHeight(realHeight);
    }

    private void updateView(PropertyChangeEvent evt) {
        if (stateMachine.isGameOver()) {
            Platform.exit();
        } else {
            Object oldV = evt.getOldValue();
            Object newV = evt.getNewValue();
            if (oldV != null) {
                IController oldVal = views.get((Class<?>) oldV);
                oldVal.hide();
            }
            IController newVal = views.get((Class<?>) newV);
            newVal.show();
        }
    }

    public File fileChooserPAOpen(String initialDir, String title) {
        FileChooser fc = fileChooserPA(initialDir, title);
        return fc.showOpenDialog(stage);
    }

    public File fileChooserPASave(String initialDir, String title) {
        FileChooser fc = fileChooserPA(initialDir, title);
        return fc.showSaveDialog(stage);
    }

    private FileChooser fileChooserPA(String initialDir, String title) {
        FileChooser fc = new FileChooser();
        File file = new File(initialDir);
        if (file.exists() && file.isDirectory()) {
            fc.setInitialDirectory(file);
        }
        fc.setTitle(title);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PA Files", "*.pa"));
        return fc;
    }

    private void initStateHandlers() throws IOException {
        for (EState e : EState.values()) {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/" + e.getName() + ".fxml"));
            Parent p = fxml.load();
            IController c = (IController) fxml.getController();
            c.setRoot(p);
            views.put(e.getStateClass(), c);
        }
    }

    public Image getAppIcon() {
        return icon;
    }

    public static Connect4App getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
