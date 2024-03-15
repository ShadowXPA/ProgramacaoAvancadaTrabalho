package pt.isec.a2019112789.connect4s.game.logic.states;

import pt.isec.a2019112789.connect4s.game.logic.data.GameData;

public class PlayerTurnState extends State {

    public PlayerTurnState(GameData gameData) {
        super(gameData);
    }

    @Override
    public IState rollback(int numRollbacks) {
        getGameData().rollback(numRollbacks);
        return this;
    }

    @Override
    public IState placeDisc(int column) {
        GameData gd = getGameData();
        if (gd.addDisc(column)) {
            return new CheckBoardState(gd);
        }
        return this;
    }

    @Override
    public IState placeSpecialDisc(int column) {
        GameData gd = getGameData();
        if (gd.addSpecialDisc(column)) {
            return new CheckBoardState(gd);
        }
        return this;
    }

    @Override
    public IState exit() {
        return new MainMenuState(getGameData());
    }
}
