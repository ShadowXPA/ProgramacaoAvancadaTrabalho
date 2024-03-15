package pt.isec.a2019112789.connect4s.game.logic.states;

import pt.isec.a2019112789.connect4s.game.logic.data.GameData;
import pt.isec.a2019112789.connect4s.game.logic.data.MathMinigame;

public class MathGameState extends MinigameAbstractState {

    public MathGameState(MathMinigame minigame, GameData gameData) {
        super(minigame, gameData);
    }

    @Override
    public IState startMinigame() {
        if (getStartTime() == 0) {
            GameData gd = getGameData();
            gd.addMessageLog("Player has " + getMinigame().getNumSeconds() + " seconds to answer.");
            setStartTime(System.currentTimeMillis());
        }
        getMinigame().newProblem();
        return this;
    }

    @Override
    public IState inputSolution(String solution) {
        GameData gd = getGameData();
        if (getMinigame().evaluateProblem(solution)) {
            gd.addMessageLog("Player responded correctly to the math problem!");
        } else {
            gd.addMessageLog("Player did not respond correctly to the math problem!");
        }
        return this;
    }
}
