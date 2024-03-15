package pt.isec.a2019112789.connect4s.game.logic.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.StringJoiner;

public class GameData implements Serializable {

    private Random rand;
    private Board board;
    private ArrayList<Player> players;
    private int maxPlayers;
    private int currentPlayer;
    private int nextMinigame;
    private int moveID;
    private int currentTotalCredits;
    private SizedStack<Board> savedBoards;
    private ArrayList<String> saveGameLogs;
    private transient Minigame minigame;
    private transient StringBuilder logs;
    private transient boolean gameOver;

    private static final int MAX_MINIGAMES = 2;

    public GameData(Random rand) {
        this(rand, 4);
    }

    public GameData(Random rand, int maxPlayers) {
        this.rand = rand;
        this.maxPlayers = maxPlayers;
        logs = new StringBuilder();
    }

    public GameData(GameData gd) {
        this.rand = gd.rand;
        this.board = new Board(gd.board);
        this.players = new ArrayList<>(gd.maxPlayers);
        for (int i = 0; i < gd.maxPlayers; i++) {
            this.players.add(gd.players.get(i).newInstance());
        }
        this.maxPlayers = gd.maxPlayers;
        this.currentPlayer = gd.currentPlayer;
        this.nextMinigame = gd.nextMinigame;
        this.logs = new StringBuilder(gd.logs.toString());
        this.saveGameLogs = new ArrayList<>(gd.saveGameLogs);
        int count = 0;
        count = gd.players.stream().map(p -> p.getCredits()).reduce(count, Integer::sum);
        this.savedBoards = new SizedStack<>(count);
        gd.savedBoards.forEach(b -> this.savedBoards.add(new Board(b)));
        this.gameOver = gd.gameOver;
        this.currentTotalCredits = gd.currentTotalCredits;
        this.moveID = gd.moveID;
        this.minigame = gd.minigame;
    }

    //<editor-fold defaultstate="collapsed" desc="Initialize">
    public final void initialize() {
        board = new Board();
        players = new ArrayList<>(maxPlayers);
        nextMinigame = rand.nextInt(MAX_MINIGAMES);
        saveGameLogs = new ArrayList<>();
        currentTotalCredits = 0;
        moveID = 0;
        inititalizeAfterReadingFile();
    }

    public final void preGameInitialize() {
        currentTotalCredits = players.stream().map(p -> p.getCredits()).reduce(currentTotalCredits, Integer::sum);
        savedBoards = new SizedStack<>(currentTotalCredits + 1);
        savedBoards.push(new Board(board));
        currentPlayer = rand.nextInt(players.size());
        StringJoiner sj = new StringJoiner("\n---------\nvs\n---------\n");
        players.forEach(p -> {
            sj.add("Player: " + p);
        });
        addSaveGameLog(sj.toString());
    }

    public final void inititalizeAfterReadingFile() {
        logs = new StringBuilder();
        gameOver = false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public final Random getRandom() {
        return rand;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Logs">
    public final String getMessageLog() {
        return logs.toString();
    }

    public final void addMessageLog(String message) {
        logs.append(message);
        logs.append("\n");
    }

    public final void clearMessageLog() {
        logs = new StringBuilder();
    }

    public final List<String> getSaveGameLog() {
        return Collections.unmodifiableList(saveGameLogs);
    }

    public final void addSaveGameLog(String message) {
        saveGameLogs.add(message);
    }

    public final void removeSaveLog(int times) {
        for (int i = 0; i < times; i++) {
            saveGameLogs.remove(saveGameLogs.size() - 1);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Connect 4">
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public int getMoveID() {
        return moveID;
    }

    public Player getPlayer(int index) {
        if (index < 0 || index >= maxPlayers) {
            return null;
        }

        return players.get(index);
    }

    public Player getCurrentPlayer() {
        return getPlayer(currentPlayer);
    }

    public int getNumPlayers() {
        return players.size();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    private void setNextPlayer() {
        currentPlayer = set_Player(++currentPlayer);
    }

    private void setPreviousPlayer() {
        currentPlayer = set_Player(--currentPlayer);
    }

    private int set_Player(int num) {
        return Math.floorMod(num, getNumPlayers());
    }

    private void setNextMove() {
        getCurrentPlayer().incrementMinigameCounter();
        setNextPlayer();
        moveID++;
    }

    private void setPreviousMove() {
        setPreviousPlayer();
        moveID--;
    }

    public final boolean addPlayer(Player p) {
        if (getNumPlayers() >= getMaxPlayers()) {
            addMessageLog("Maximum number of players has been reached.");
            return false;
        }
        if (players.contains(p)) {
            addMessageLog("Player already exists.");
            return false;
        }
        if (p.getName().isBlank()) {
            addMessageLog("Invalid name!");
            return false;
        }
        if (p.getDisc() == null || p.getDisc() == EDisc.Empty || p.getDisc() == EDisc.Special) {
            addMessageLog("Invalid disc!");
            return false;
        }
        players.add(p);
        addMessageLog("Player '" + p.getName() + "' has been added.");
        return true;
    }

    public final boolean removePlayer(String name) {
        boolean removed = players.removeIf(p -> p.getName().equals(name));
        if (removed) {
            addMessageLog("Player '" + name + "' has been removed.");
        } else {
            addMessageLog("Could not remove player '" + name + "'.");
            addMessageLog("Might not exist.");
        }

        return removed;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Minigame">
    public final void setMinigame(Minigame minigame) {
        this.minigame = minigame;
    }

    private void setNextMinigame() {
        nextMinigame = Math.floorMod(++nextMinigame, MAX_MINIGAMES);
    }

    public int getAndSetNextMinigame() {
        int temp = getNextMinigame();
        setNextMinigame();
        return temp;
    }

    public final Minigame getMinigame() {
        return this.minigame;
    }

    public final boolean isMinigameAvailable() {
        return getCurrentPlayer().isAvailableForMinigame() && moveID > getNumPlayers();
    }

    public int getNextMinigame() {
        return nextMinigame;
    }

    public void playerWonMinigame() {
        Player curPlayer = getCurrentPlayer();
        addSaveGameLog("Player '" + curPlayer.getName() + "' won a minigame!");
        addMessageLog("Player '" + curPlayer.getName() + "' won a minigame!");
        curPlayer.incrementSP();
        curPlayer.resetMinigameCounter();
        this.minigame = null;
    }

    public void playerLostMinigame() {
        Player curPlayer = getCurrentPlayer();
        addSaveGameLog("Player '" + curPlayer.getName() + "' lost a minigame!");
        addMessageLog("Player '" + curPlayer.getName() + "' lost a minigame!");
        curPlayer.resetMinigameCounter();
        setNextMove();
        this.minigame = null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Board">
    public final void replayDisc(EDisc disc, int column) {
        board.addDisc(disc, column);
    }

    public final Player getWinner() {
        EDisc discWinner = board.getWinner();
        if (discWinner == EDisc.Empty && board.isFull()) {
            // If winner is not set and board is full then it's a tie
            return new Computer("None", EDisc.Empty);
        }
        Optional<Player> winner = players.stream().filter(p -> p.getDisc() == discWinner).findFirst();
        return winner.isEmpty() ? null : winner.orElseThrow();
    }

    public final EDisc[][] getBoard() {
        return board.getBoard();
    }

    public final int getMaxCols() {
        return board.getNumCols();
    }

    public final int getMaxRows() {
        return board.getNumRows();
    }

    public boolean addDisc(int column) {
        EDisc disc = getCurrentPlayer().getDisc();
        boolean added = board.addDisc(disc, column);
        if (added) {
            addSaveGameLog("place " + disc.getChar() + ":" + column);
            addMessageLog("Player '" + getCurrentPlayer().getName() + "' placed a piece on column " + column);
            saveBoard();
        } else {
            addMessageLog("Disc was not placed at column " + column + "!");
            addMessageLog("Column might be full or not exist.");
        }
        return added;
    }

    public boolean addSpecialDisc(int column) {
        if (getCurrentPlayer().getSPCount() <= 0) {
            addMessageLog("No special discs available.");
            return false;
        }
        boolean added = board.addDisc(EDisc.Special, column);
        if (added) {
            addSaveGameLog("place " + EDisc.Special.getChar() + ":" + column);
            getCurrentPlayer().decrementSP();
            addMessageLog("Player '" + getCurrentPlayer().getName() + "' placed a special piece on column " + column);
            saveBoard();
        } else {
            addMessageLog("Disc was not placed");
        }
        return added;
    }

    public void rollback(int numRollbacks) {
        if (savedBoards.isEmpty()
                || (getCurrentPlayer().getCredits() - numRollbacks) < 0
                || (savedBoards.size() - (numRollbacks + 1)) < 0
                || numRollbacks == 0) {
            addMessageLog("No more than " + numRollbacks + " rollback(s) allowed.");
            return;
        }
        int temp = currentTotalCredits;
        savedBoards.pop();
        if (!savedBoards.isEmpty()) {
            for (int i = 0; i < numRollbacks; i++) {
                board = savedBoards.pop();
                getCurrentPlayer().decrementCredits();
                currentTotalCredits--;
            }
        } else {
            board = new Board(board.getNumRows(), board.getNumCols());
        }
        savedBoards.push(new Board(board));
        removeSaveLog(numRollbacks);
        getCurrentPlayer().resetMinigameCounter();
        addMessageLog("Player rolled back " + numRollbacks + " move(s).");
        for (int i = 0; i < (temp - currentTotalCredits); i++) {
            setPreviousMove();
        }
    }

    public final void resetBoard() {
        board = new Board();
    }

    private void saveBoard() {
        if (currentTotalCredits > 0) {
            savedBoards.push(new Board(board));
            addMessageLog("Board state saved.");
        }
        Player winner = getWinner();
        if (winner != null) {
            addSaveGameLog("Winner: '" + winner.getName() + "' Piece: '" + winner.getDisc().getChar() + "'");
        }
        setNextMove();
    }
    //</editor-fold>
}
