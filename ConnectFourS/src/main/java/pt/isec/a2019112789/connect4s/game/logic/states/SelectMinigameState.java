package pt.isec.a2019112789.connect4s.game.logic.states;

import java.io.IOException;
import pt.isec.a2019112789.connect4s.game.logic.data.GameData;
import pt.isec.a2019112789.connect4s.game.logic.data.MathMinigame;
import pt.isec.a2019112789.connect4s.game.logic.data.WordMinigame;

public class SelectMinigameState extends State {

    public SelectMinigameState(GameData gameData) {
        super(gameData);
    }

    @Override
    public IState playMinigame() {
        GameData gd = getGameData();

        switch (gd.getAndSetNextMinigame()) {
            case 0 -> {
                try {
                    return new WordGameState(new WordMinigame(gd.getRandom()), getGameData());
                } catch (IOException ex) {
                    gd.addMessageLog("Can not find file.");
                    gd.addMessageLog("Switching minigame.");
                }
            }
            case 1 -> {
                return new MathGameState(new MathMinigame(gd.getRandom()), getGameData());
            }
            default -> {
            }
        }

        return this;
    }
}
