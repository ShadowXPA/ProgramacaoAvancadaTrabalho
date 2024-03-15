package pt.isec.a2019112789.connect4s.game.logic;

import java.io.File;
import java.util.List;
import java.util.Random;
import pt.isec.a2019112789.connect4s.game.logic.data.EDisc;
import pt.isec.a2019112789.connect4s.game.logic.data.GameData;
import pt.isec.a2019112789.connect4s.game.logic.data.Minigame;
import pt.isec.a2019112789.connect4s.game.logic.data.Player;
import pt.isec.a2019112789.connect4s.game.logic.states.IState;
import pt.isec.a2019112789.connect4s.game.logic.states.MainMenuState;

public final class StateMachine {

    private Random rand;
    private IState currentState;

    public StateMachine() {
        initialize();
    }

    //<editor-fold defaultstate="collapsed" desc="Initialize">
    public final void initialize() {
        this.rand = new Random();
        this.currentState = new MainMenuState(new GameData(this.rand));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters">
    protected final GameData getGameData() {
        return currentState.getGameData();
    }

    public final File[] getReplays() {
        File dir = new File("Replays");
        if (dir.exists() && dir.isDirectory()) {
            return dir.listFiles((file, name) -> {
                return name.endsWith(".pa");
            });
        }
        return new File[0];
    }

    public final Class<?> getStateClass() {
        return currentState.getClass();
    }

    public final boolean isGameOver() {
        return getGameData().isGameOver();
    }

    protected final String getMessageLog() {
        return getGameData().getMessageLog();
    }

    protected final void clearMessageLog() {
        getGameData().clearMessageLog();
    }

    public final String getMessageLogAndClearLog() {
        String tmp = getMessageLog();
        clearMessageLog();
        return tmp;
    }

    //<editor-fold defaultstate="collapsed" desc="Current Player">
    protected final Player getCurrentPlayer() {
        return getGameData().getCurrentPlayer();
    }

    public final String getCurrentPlayerString() {
        return getCurrentPlayer().toString();
    }

    public final String getCurrentPlayerName() {
        return getCurrentPlayer().getName();
    }

    public final EDisc getCurrentPlayerDisc() {
        return getCurrentPlayer().getDisc();
    }

    public final int getCurrentPlayerCredits() {
        return getCurrentPlayer().getCredits();
    }

    public final int getCurrentPlayerSpecialPiece() {
        return getCurrentPlayer().getSPCount();
    }

    public final int getCurrentPlayerMinigameCounter() {
        return getCurrentPlayer().getMinigameCounter();
    }

    public final boolean isCurrentPlayerBot() {
        return getCurrentPlayer().isBot();
    }

    public final int getCurrentPlayerNextMove() {
        return getCurrentPlayer().getNextMove(rand, getGameData().getMaxCols());
    }
    //</editor-fold>

    public final List<Player> getPlayers() {
        return getGameData().getPlayers();
    }

    public final int getNumPlayers() {
        return getGameData().getNumPlayers();
    }

    public final EDisc[][] getBoard() {
        return getGameData().getBoard();
    }

    public final int getMaxCols() {
        return getGameData().getMaxCols();
    }

    public final int getMaxRows() {
        return getGameData().getMaxRows();
    }

    //<editor-fold defaultstate="collapsed" desc="Winner">
    protected final Player getWinner() {
        return getGameData().getWinner();
    }

    public final String getWinnerString() {
        return getWinner().toString();
    }

    public final String getWinnerName() {
        return getWinner().getName();
    }

    public final EDisc getWinnerDisc() {
        return getWinner().getDisc();
    }

    public final boolean isWinnerBot() {
        return getWinner().isBot();
    }
    //</editor-fold>

    public final int getMoveID() {
        return getGameData().getMoveID();
    }

    //<editor-fold defaultstate="collapsed" desc="Minigame">
    protected final Minigame getMinigame() {
        return getGameData().getMinigame();
    }

    public final int getMinigameNumSeconds() {
        return getMinigame().getNumSeconds();
    }

    public final int getMinigameNumCorrect() {
        return getMinigame().getNumCorrect();
    }

    public final int getMinigameTries() {
        return getMinigame().getTries();
    }

    public final int getMinigameCorrect() {
        return getMinigame().getCorrect();
    }

    public final int getMinigameWrong() {
        return getMinigame().getWrong();
    }

    public final String getMinigameProblem() {
        return getMinigame().getProblem();
    }

    public final long getMinigameElapsedSeconds() {
        return getMinigame().getElapsedSeconds();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="State Functions">
    public final void play() {
        currentState = currentState.play();
    }

    public final void exit() {
        currentState = currentState.exit();
    }

    public final void load(String fileName) {
        currentState = currentState.load(fileName);
    }

    public final void replay(String fileName) {
        currentState = currentState.replay(fileName);
    }

    public final void replayStep() {
        currentState = currentState.replayStep();
    }

    public final void configGame() {
        currentState = currentState.configGame();
    }

    public final void addHumanPlayer(String playerName, char playerColor) {
        currentState = currentState.addHumanPlayer(playerName, playerColor);
    }

    public final void addComputerPlayer(String playerName, char playerColor) {
        currentState = currentState.addComputerPlayer(playerName, playerColor);
    }

    public final void removePlayer(String playerName) {
        currentState = currentState.removePlayer(playerName);
    }

    public final void startGame() {
        currentState = currentState.startGame();
    }

    public final void save(String fileName) {
        currentState = currentState.save(fileName);
    }

    public final void placeDisc(int column) {
        currentState = currentState.placeDisc(column);
    }

    public final void placeSpecialDisc(int column) {
        currentState = currentState.placeSpecialDisc(column);
    }

    public final void rollback(int numRollbacks) {
        currentState = currentState.rollback(numRollbacks);
    }

    public final void checkMinigameAvailability() {
        currentState = currentState.checkMinigameAvailability();
    }

    public final void checkBoard() {
        currentState = currentState.checkBoard();
    }

    public final void playConnect4() {
        currentState = currentState.playConnect4();
    }

    public final void playMinigame() {
        currentState = currentState.playMinigame();
    }

    public final void startMinigame() {
        currentState = currentState.startMinigame();
    }

    public final void inputSolution(String solution) {
        currentState = currentState.inputSolution(solution);
    }

    public final void endMinigame() {
        currentState = currentState.endMinigame();
    }

    public final void playAgain() {
        currentState = currentState.playAgain();
    }
    //</editor-fold>
}
