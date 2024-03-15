package pt.isec.a2019112789.connect4s.game.logic.states;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import pt.isec.a2019112789.connect4s.game.logic.data.GameData;

public class MainMenuState extends State {

    public MainMenuState(GameData gameData) {
        super(gameData);
    }

    @Override
    public IState play() {
        return new ConfigureGameState(getGameData());
    }

    @Override
    public IState exit() {
        getGameData().setGameOver(true);
        return this;
    }

    @Override
    public IState load(String fileName) {
        GameData loadedGame = readGameDataFromFile(fileName);
        GameData gd = getGameData();
        if (loadedGame != null) {
            loadedGame.inititalizeAfterReadingFile();
            setGameData(loadedGame);
            gd.addMessageLog("Game loaded from file.");
            return new CheckBoardState(loadedGame);
        }

        return this;
    }

    @Override
    public IState replay(String fileName) {
        GameData gd = getGameData();
        GameData loadedGame = readGameDataFromFile(fileName);
        if (loadedGame != null) {
            loadedGame.inititalizeAfterReadingFile();
            if (loadedGame.getWinner() == null) {
                gd.addMessageLog("Game has not finished yet!");
                return this;
            }
            setGameData(loadedGame);
            return new ReplayState(loadedGame);
        }

        return this;
    }

    private GameData readGameDataFromFile(String fileName) {
        fileName = fileName.endsWith(".pa") ? fileName : fileName + ".pa";
        File file = new File(fileName);
        GameData loadedGame;
        GameData gd = getGameData();
        try {
            try ( FileInputStream fIS = new FileInputStream(file);  ObjectInputStream oIS = new ObjectInputStream(fIS)) {
                loadedGame = (GameData) oIS.readUnshared();
            }
        } catch (FileNotFoundException ex) {
            gd.addMessageLog("File '" + fileName + "' was not found!");
            gd.addMessageLog(ex.getMessage());
            return null;
        } catch (IOException ex) {
            gd.addMessageLog("IOException when reading from file: '" + fileName + "'.");
            gd.addMessageLog(ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            gd.addMessageLog("ClassNotFoundException when reading from file: '" + fileName + "'.");
            gd.addMessageLog(ex.getMessage());
            return null;
        }
        return loadedGame;
    }
}
