package pt.isec.a2019112789.connect4s.game.logic.states;

import pt.isec.a2019112789.connect4s.game.logic.data.GameData;

public class MinigameAvailabilityState extends State {

    public MinigameAvailabilityState(GameData gameData) {
        super(gameData);
    }

    @Override
    public IState checkMinigameAvailability() {
        GameData gd = getGameData();
        return gd.isMinigameAvailable() ? new MinigameState(gd) : new PlayerTurnState(gd);
    }
}
