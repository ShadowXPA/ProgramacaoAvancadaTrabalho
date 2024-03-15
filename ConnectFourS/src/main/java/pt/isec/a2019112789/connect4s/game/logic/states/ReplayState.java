package pt.isec.a2019112789.connect4s.game.logic.states;

import java.util.List;
import pt.isec.a2019112789.connect4s.game.logic.data.EDisc;
import pt.isec.a2019112789.connect4s.game.logic.data.GameData;

public class ReplayState extends State {

    private final List<String> stepLog;
    private int stepIndex;

    public ReplayState(GameData gameData) {
        super(gameData);
        this.stepLog = getGameData().getSaveGameLog();
        this.stepIndex = 0;
        getGameData().resetBoard();
    }

    @Override
    public IState replayStep() {
        String step = stepLog.get(stepIndex);
        GameData gd = getGameData();
        gd.addMessageLog(step);

        if (stepIndex == (stepLog.size() - 1)) {
            return new MainMenuState(gd);
        }

        if (step.startsWith("place")) {
            String[] command = step.replace("place ", "").split(":");
            EDisc disc = EDisc.getDisc(command[0].charAt(0));
            int column = Integer.parseInt(command[1]);
            gd.replayDisc(disc, column);
        }

        stepIndex++;
        return this;
    }
}
