package pt.isec.a2019112789.connect4s.game.logic.states;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import pt.isec.a2019112789.connect4s.game.logic.data.GameData;

public abstract class State implements IState {

    private GameData gameData;

    public State(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public final GameData getGameData() {
        return gameData;
    }

    protected final void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public IState play() {
        return this;
    }

    @Override
    public IState exit() {
        return this;
    }

    @Override
    public IState load(String fileName) {
        return this;
    }

    @Override
    public IState replay(String fileName) {
        return this;
    }

    @Override
    public IState configGame() {
        return this;
    }

    @Override
    public IState checkMinigameAvailability() {
        return this;
    }

    @Override
    public IState checkBoard() {
        return this;
    }

    @Override
    public IState addHumanPlayer(String playerName, char playerColor) {
        return this;
    }

    @Override
    public IState addComputerPlayer(String playerName, char playerColor) {
        return this;
    }

    @Override
    public IState removePlayer(String playerName) {
        return this;
    }

    @Override
    public IState startGame() {
        return this;
    }

    @Override
    public IState save(String fileName) {
        fileName = fileName.endsWith(".pa") ? fileName : fileName + ".pa";
        File file = new File(fileName);
        GameData gd = getGameData();
        try {
            try ( FileOutputStream fOS = new FileOutputStream(file);  ObjectOutputStream oOS = new ObjectOutputStream(fOS)) {
                oOS.writeUnshared(gd);
                oOS.flush();
            }
        } catch (FileNotFoundException ex) {
            gd.addMessageLog("File '" + fileName + "' was not found!");
            return this;
        } catch (IOException ex) {
            gd.addMessageLog("IOException when writting to file: '" + fileName + "'.");
            gd.addMessageLog(ex.getMessage());
            return this;
        }

        return this;
    }

    @Override
    public IState placeDisc(int column) {
        return this;
    }

    @Override
    public IState placeSpecialDisc(int column) {
        return this;
    }

    @Override
    public IState rollback(int numRollbacks) {
        return this;
    }

    @Override
    public IState playMinigame() {
        return this;
    }

    @Override
    public IState startMinigame() {
        return this;
    }

    @Override
    public IState endMinigame() {
        return this;
    }

    @Override
    public IState inputSolution(String solution) {
        return this;
    }

    @Override
    public IState playConnect4() {
        return this;
    }

    @Override
    public IState playAgain() {
        return this;
    }

    @Override
    public IState replayStep() {
        return this;
    }
}
