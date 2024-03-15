package pt.isec.a2019112789.connect4s.game.logic.states;

import pt.isec.a2019112789.connect4s.game.logic.data.GameData;

public class GameOverState extends State {

    public GameOverState(GameData gameData) {
        super(gameData);
    }

    @Override
    public IState playAgain() {
        return new ConfigureGameState(getGameData());
    }

    @Override
    public IState exit() {
        return new MainMenuState(getGameData());
    }
}
