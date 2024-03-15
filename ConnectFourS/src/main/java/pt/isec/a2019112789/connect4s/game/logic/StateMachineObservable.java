package pt.isec.a2019112789.connect4s.game.logic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;
import pt.isec.a2019112789.connect4s.game.logic.data.EDisc;
import pt.isec.a2019112789.connect4s.game.logic.data.Player;

public final class StateMachineObservable {

    private final StateMachine stateMachine;
    private final PropertyChangeSupport propertyChangeSupport;

    private static final String STATE_PROPERTY = "currentState";

    public StateMachineObservable(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
        propertyChangeSupport = new PropertyChangeSupport(stateMachine);
    }

    //<editor-fold defaultstate="collapsed" desc="Register Listener">
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        addPropertyChangeListener(STATE_PROPERTY, propertyChangeListener);
    }

    protected void addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="State Machine">
    //<editor-fold defaultstate="collapsed" desc="Initialize">
    public final void initialize() {
        stateMachine.initialize();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public final File[] getReplays() {
        return stateMachine.getReplays();
    }

    public final Class<?> getStateClass() {
        return stateMachine.getStateClass();
    }

    public final boolean isGameOver() {
        return stateMachine.isGameOver();
    }

    public final String getMessageLogAndClearLog() {
        return stateMachine.getMessageLogAndClearLog();
    }

    //<editor-fold defaultstate="collapsed" desc="Current Player">
    protected final Player getCurrentPlayer() {
        return stateMachine.getCurrentPlayer();
    }

    public final String getCurrentPlayerString() {
        return stateMachine.getCurrentPlayerString();
    }

    public final String getCurrentPlayerName() {
        return stateMachine.getCurrentPlayerName();
    }

    public final EDisc getCurrentPlayerDisc() {
        return stateMachine.getCurrentPlayerDisc();
    }

    public final int getCurrentPlayerCredits() {
        return stateMachine.getCurrentPlayerCredits();
    }

    public final int getCurrentPlayerSpecialPiece() {
        return stateMachine.getCurrentPlayerSpecialPiece();
    }

    public final int getCurrentPlayerMinigameCounter() {
        return stateMachine.getCurrentPlayerMinigameCounter();
    }

    public final boolean isCurrentPlayerBot() {
        return stateMachine.isCurrentPlayerBot();
    }

    public final int getCurrentPlayerNextMove() {
        return stateMachine.getCurrentPlayerNextMove();
    }
    //</editor-fold>

    public final List<Player> getPlayers() {
        return stateMachine.getPlayers();
    }

    public final int getNumPlayers() {
        return stateMachine.getNumPlayers();
    }

    public final EDisc[][] getBoard() {
        return stateMachine.getBoard();
    }

    public final int getMaxCols() {
        return stateMachine.getMaxCols();
    }

    public final int getMaxRows() {
        return stateMachine.getMaxRows();
    }

    //<editor-fold defaultstate="collapsed" desc="Winner">
    protected final Player getWinner() {
        return stateMachine.getWinner();
    }

    public final String getWinnerString() {
        return stateMachine.getWinnerString();
    }

    public final String getWinnerName() {
        return stateMachine.getWinnerName();
    }

    public final EDisc getWinnerDisc() {
        return stateMachine.getWinnerDisc();
    }

    public final boolean isWinnerBot() {
        return stateMachine.isWinnerBot();
    }
    //</editor-fold>

    public final int getMoveID() {
        return stateMachine.getMoveID();
    }

    //<editor-fold defaultstate="collapsed" desc="Minigame">
    public final int getMinigameNumSeconds() {
        return stateMachine.getMinigameNumSeconds();
    }

    public final int getMinigameNumCorrect() {
        return stateMachine.getMinigameNumCorrect();
    }

    public final int getMinigameTries() {
        return stateMachine.getMinigameTries();
    }

    public final int getMinigameCorrect() {
        return stateMachine.getMinigameCorrect();
    }

    public final int getMinigameWrong() {
        return stateMachine.getMinigameWrong();
    }

    public final String getMinigameProblem() {
        return stateMachine.getMinigameProblem();
    }

    public final long getMinigameElapsedSeconds() {
        return stateMachine.getMinigameElapsedSeconds();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="State Functions">
    public final void play() {
        Class<?> stateClass = getStateClass();
        stateMachine.play();
        firePropertyChangeStateClass(stateClass);
    }

    public final void exit() {
        Class<?> stateClass = getStateClass();
        stateMachine.exit();
        firePropertyChangeStateClass(stateClass);
    }

    public final void load(String fileName) {
        Class<?> stateClass = getStateClass();
        stateMachine.load(fileName);
        firePropertyChangeStateClass(stateClass);
    }

    public final void replay(String fileName) {
        Class<?> stateClass = getStateClass();
        stateMachine.replay(fileName);
        firePropertyChangeStateClass(stateClass);
    }

    public final void replayStep() {
        Class<?> stateClass = getStateClass();
        stateMachine.replayStep();
        firePropertyChangeStateClass(stateClass);
    }

    public final void configGame() {
        Class<?> stateClass = getStateClass();
        stateMachine.configGame();
        firePropertyChangeStateClass(stateClass);
    }

    public final void addPlayer(Player player) {
        Class<?> stateClass = getStateClass();
        if (player != null && player.isBot()) {
            stateMachine.addComputerPlayer(player.getName(), player.getDisc().getChar());
        } else if (player != null && !player.isBot()) {
            stateMachine.addHumanPlayer(player.getName(), player.getDisc().getChar());
        }
        firePropertyChangeStateClass(stateClass);
    }

    public final void removePlayer(String playerName) {
        Class<?> stateClass = getStateClass();
        stateMachine.removePlayer(playerName);
        firePropertyChangeStateClass(stateClass);
    }

    public final void startGame() {
        Class<?> stateClass = getStateClass();
        stateMachine.startGame();
        firePropertyChangeStateClass(stateClass);
    }

    public final void save(String fileName) {
        Class<?> stateClass = getStateClass();
        stateMachine.save(fileName);
        firePropertyChangeStateClass(stateClass);
    }

    public final void placeDisc(int column) {
        Class<?> stateClass = getStateClass();
        stateMachine.placeDisc(column);
        firePropertyChangeStateClass(stateClass);
    }

    public final void placeSpecialDisc(int column) {
        Class<?> stateClass = getStateClass();
        stateMachine.placeSpecialDisc(column);
        firePropertyChangeStateClass(stateClass);
    }

    public final void rollback(int numRollbacks) {
        Class<?> stateClass = getStateClass();
        stateMachine.rollback(numRollbacks);
        firePropertyChangeStateClass(stateClass);
    }

    public final void checkMinigameAvailability() {
        Class<?> stateClass = getStateClass();
        stateMachine.checkMinigameAvailability();
        firePropertyChangeStateClass(stateClass);
    }

    public final void checkBoard() {
        Class<?> stateClass = getStateClass();
        stateMachine.checkBoard();
        firePropertyChangeStateClass(stateClass);
    }

    public final void playConnect4() {
        Class<?> stateClass = getStateClass();
        stateMachine.playConnect4();
        firePropertyChangeStateClass(stateClass);
    }

    public final void playMinigame() {
        Class<?> stateClass = getStateClass();
        stateMachine.playMinigame();
        firePropertyChangeStateClass(stateClass);
    }

    public final void startMinigame() {
        Class<?> stateClass = getStateClass();
        stateMachine.startMinigame();
        //firePropertyChangeStateClass(stateClass);
    }

    public final void inputSolution(String solution) {
        Class<?> stateClass = getStateClass();
        stateMachine.inputSolution(solution);
        //firePropertyChangeStateClass(stateClass);
    }

    public final void endMinigame() {
        Class<?> stateClass = getStateClass();
        stateMachine.endMinigame();
        firePropertyChangeStateClass(stateClass);
    }

    public final void playAgain() {
        Class<?> stateClass = getStateClass();
        stateMachine.playAgain();
        firePropertyChangeStateClass(stateClass);
    }
    //</editor-fold>
    //</editor-fold>

    private boolean isNewState(Class<?> stateClass) {
        return stateClass != getStateClass();
    }

    private void firePropertyChangeStateClass(Class<?> stateClass) {
        propertyChangeSupport.firePropertyChange(STATE_PROPERTY, isNewState(stateClass) ? stateClass : null, getStateClass());
    }
}
