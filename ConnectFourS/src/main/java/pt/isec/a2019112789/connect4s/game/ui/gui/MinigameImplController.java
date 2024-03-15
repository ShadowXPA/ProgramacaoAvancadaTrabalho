package pt.isec.a2019112789.connect4s.game.ui.gui;

import java.util.concurrent.atomic.AtomicLong;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class MinigameImplController extends Controller {

    @FXML
    private Label lSeconds;
    @FXML
    private Label lProblem;
    @FXML
    private TextField tfInput;
    @FXML
    private ProgressBar pbCorrect;
    @FXML
    private Button btnInput;
    @FXML
    private Label lSeconds2;
    @FXML
    private Label lTimes;

    private Thread countdown;
    private CountSeconds countSeconds;

    private static final double WIDTH = 377;
    private static final double HEIGHT = 274;

    @Override
    public void initialize() {
        super.initialize();
        lProblem.setWrapText(true);
        lProblem.setMaxWidth(350);
        countSeconds = new CountSeconds(lSeconds, () -> {
            Platform.runLater(() -> {
                btnInput.fire();
            });
        });
    }

    @FXML
    private void onInput(ActionEvent event) {
        app.getStateMachine().inputSolution(tfInput.getText());
        app.getStateMachine().endMinigame();
    }

    @Override
    public void show() {
        super.show();
        app.setStageSizes(WIDTH, HEIGHT);
        app.getStateMachine().startMinigame();
        int numSecs = app.getStateMachine().getMinigameNumSeconds();
        long curSecs = app.getStateMachine().getMinigameElapsedSeconds();
        double numCorrect = app.getStateMachine().getMinigameCorrect();
        int maxTries = app.getStateMachine().getMinigameNumCorrect();
        String problem = app.getStateMachine().getMinigameProblem();
        long secsLeft = numSecs - curSecs;
        lSeconds.setText(String.valueOf(secsLeft));
        lSeconds2.setText(String.valueOf(numSecs));
        lProblem.setText(problem);
        lTimes.setText(String.valueOf(maxTries));
        double d = (numCorrect / maxTries);
        pbCorrect.setProgress(d);
        tfInput.setText("");
        tfInput.requestFocus();

        if (countdown != null && countdown.isAlive()) {
            countSeconds.setSeconds(secsLeft);
        } else {
            countSeconds.setSeconds(secsLeft);
            countdown = new Thread(countSeconds, "Countdown");
            countdown.setDaemon(true);
            countdown.start();
        }
    }

    @Override
    public void hide() {
        super.hide();
        tfInput.setText("");
        lProblem.setText("");
        lSeconds.setText("");
        lSeconds2.setText("");
        lTimes.setText("");
        pbCorrect.setProgress(0);
    }
}

class CountSeconds implements Runnable {

    private final AtomicLong seconds;
    private final Label lSeconds;
    private final Runnable callback;

    public CountSeconds(Label lSeconds, Runnable callback) {
        this.seconds = new AtomicLong(0);
        this.lSeconds = lSeconds;
        this.callback = callback;
    }

    public void setSeconds(long seconds) {
        this.seconds.set(seconds);
    }

    @Override
    public void run() {
        while (seconds.getAndDecrement() > 0) {
            Platform.runLater(() -> {
                lSeconds.setText(String.valueOf(seconds.get()));
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        callback.run();
    }
}
