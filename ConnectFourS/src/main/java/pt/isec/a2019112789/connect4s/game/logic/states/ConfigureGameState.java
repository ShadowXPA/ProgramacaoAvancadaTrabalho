package pt.isec.a2019112789.connect4s.game.logic.states;

import pt.isec.a2019112789.connect4s.game.logic.data.Computer;
import pt.isec.a2019112789.connect4s.game.logic.data.EDisc;
import pt.isec.a2019112789.connect4s.game.logic.data.GameData;
import pt.isec.a2019112789.connect4s.game.logic.data.Human;

public class ConfigureGameState extends State {

    public ConfigureGameState(GameData gameData) {
        super(gameData);
        gameData.initialize();
    }

    @Override
    public IState startGame() {
        GameData gd = getGameData();
        if (gd.getNumPlayers() >= 2) {
            gd.preGameInitialize();
            return new MinigameAvailabilityState(gd);
        }
        gd.addMessageLog("Not enough players to start the game!");
        return this;
    }

    @Override
    public IState addHumanPlayer(String playerName, char playerColor) {
        EDisc disc = EDisc.getDisc(playerColor);
        getGameData().addPlayer(new Human(playerName.replace("h:", ""), disc));
        return this;
    }

    @Override
    public IState addComputerPlayer(String playerName, char playerColor) {
        EDisc disc = EDisc.getDisc(playerColor);
        getGameData().addPlayer(new Computer(playerName, disc));
        return this;
    }

    @Override
    public IState removePlayer(String playerName) {
        getGameData().removePlayer(playerName);
        return this;
    }

    @Override
    public IState exit() {
        return new MainMenuState(getGameData());
    }
}
