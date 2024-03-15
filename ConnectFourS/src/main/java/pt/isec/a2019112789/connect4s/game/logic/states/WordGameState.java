package pt.isec.a2019112789.connect4s.game.logic.states;

import pt.isec.a2019112789.connect4s.game.logic.data.GameData;
import pt.isec.a2019112789.connect4s.game.logic.data.WordMinigame;

public class WordGameState extends MinigameAbstractState {

    public WordGameState(WordMinigame minigame, GameData gameData) {
        super(minigame, gameData);
    }

    @Override
    public IState startMinigame() {
        if (getStartTime() == 0) {
            GameData gd = getGameData();
            gd.addMessageLog("Player has " + getMinigame().getNumSeconds() + " seconds to answer.");
            setStartTime(System.currentTimeMillis());
            getMinigame().newProblem();
        }
        return this;
    }

    @Override
    public IState inputSolution(String solution) {
        GameData gd = getGameData();
        if (getMinigame().evaluateProblem(solution)) {
            gd.addMessageLog("Player got the words correct!");
        } else {
            gd.addMessageLog("Player did not get the words correct!");
        }
        return this;
    }
}
