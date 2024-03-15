package pt.isec.a2019112789.connect4s.game.logic.states;

import pt.isec.a2019112789.connect4s.game.logic.data.GameData;

public class MinigameState extends State {

    public MinigameState(GameData gameData) {
        super(gameData);
    }

    @Override
    public IState playConnect4() {
        return new PlayerTurnState(getGameData());
    }

    @Override
    public IState playMinigame() {
        return new SelectMinigameState(getGameData());
    }
}
