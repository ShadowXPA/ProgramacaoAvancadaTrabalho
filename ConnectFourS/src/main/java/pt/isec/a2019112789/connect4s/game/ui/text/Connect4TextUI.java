package pt.isec.a2019112789.connect4s.game.ui.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import pt.isec.a2019112789.connect4s.game.logic.EState;
import pt.isec.a2019112789.connect4s.game.logic.data.EDisc;
import pt.isec.a2019112789.connect4s.game.logic.data.Player;
import pt.isec.a2019112789.connect4s.game.logic.StateMachine;

public class Connect4TextUI implements Runnable {

    private StateMachine stateMachine;
    private final HashMap<Class<?>, Consumer<?>> stateHandlers;
    private final BufferedWriter writer;
    private final BufferedReader reader;

    public Connect4TextUI(OutputStream writer, InputStream reader) {
        this.writer = new BufferedWriter(new OutputStreamWriter(writer));
        this.reader = new BufferedReader(new InputStreamReader(reader));
        this.stateHandlers = new HashMap<>();
        this.stateMachine = null;
    }

    // <editor-fold desc="Main StateMachine Loop">
    @Override
    public void run() {
        if (stateMachine == null) {
            return;
        }

        while (!stateMachine.isGameOver()) {
            try {
                update();
                Consumer<?> action = stateHandlers.get(stateMachine.getStateClass());
                if (action != null) {
                    action.accept(null);
                } else {
                    writeLine("Not yet implemented!");
                    // Go back to main menu...
                    stateMachine.initialize();
                }
            } catch (IOException ex) {
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Initialize">
    public void initialize() {
        // Initialize the game
        stateMachine = new StateMachine();
        initStateHandlers();
    }

    private void initStateHandlers() {
        stateHandlers.put(EState.MainMenu.getStateClass(), (t) -> {
            try {
                menuMain();
            } catch (Exception ex) {
            }
        });
        stateHandlers.put(EState.ConfigureGame.getStateClass(), (t) -> {
            try {
                menuConfigGame();
            } catch (Exception ex) {
            }
        });
        stateHandlers.put(EState.PlayerTurn.getStateClass(), (t) -> {
            try {
                menuPlayerTurn();
            } catch (Exception ex) {
            }
        });
        stateHandlers.put(EState.MinigameAvailability.getStateClass(), (t) -> {
            stateMachine.checkMinigameAvailability();
        });
        stateHandlers.put(EState.CheckBoard.getStateClass(), (t) -> {
            stateMachine.checkBoard();
        });
        stateHandlers.put(EState.Minigame.getStateClass(), (t) -> {
            try {
                menuPlayMinigame();
            } catch (Exception ex) {
            }
        });
        stateHandlers.put(EState.GameOver.getStateClass(), (t) -> {
            try {
                menuGameOver();
            } catch (Exception ex) {
            }
        });
        stateHandlers.put(EState.Replay.getStateClass(), (t) -> {
            try {
                showBoard();
                stateMachine.replayStep();
                Thread.sleep(1000);
            } catch (Exception ex) {
            }
        });
        stateHandlers.put(EState.SelectMinigame.getStateClass(), (t) -> {
            stateMachine.playMinigame();
        });
        stateHandlers.put(EState.MathMinigame.getStateClass(), (t) -> {
            try {
                menuMinigame();
            } catch (IOException ex) {
            }
        });
        stateHandlers.put(EState.WordMinigame.getStateClass(), (t) -> {
            try {
                menuMinigame();
            } catch (IOException ex) {
            }
        });
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Prints">
    public void update() throws IOException {
        write("\n");
        writeLine("Logs:");
        writeLine(stateMachine.getMessageLogAndClearLog());
    }

    public void showPlayers() throws IOException {
        int numPlayers = stateMachine.getNumPlayers();
        List<Player> players = stateMachine.getPlayers();

        writeLine("Number of players: " + numPlayers);
        for (Player p : players) {
            writeLine("Player:" + p);
        }
        write("\n");
    }

    public void showDiscs() throws IOException {
        write("\n");
        for (EDisc d : EDisc.values()) {
            if (d != EDisc.Empty && d != EDisc.Special) {
                writeLine("Disc:" + d.toString());
            }
        }
        write("\n");
    }

    public void showCurrentPlayer() throws IOException {
        String currentPlayer = stateMachine.getCurrentPlayerString();
        writeLine("Player:" + currentPlayer);
        write("\n");
    }

    public void showBoard() throws IOException {
        EDisc[][] board = stateMachine.getBoard();
        for (EDisc[] x : board) {
            write("|");
            for (EDisc y : x) {
                char ch = y.getChar();
                if (ch == EDisc.Empty.getChar()) {
                    ch = ' ';
                }
                write("" + ch + "|");
            }
            write("\n");
        }
        write("\n");
    }

    public void showMoveID() throws IOException {
        writeLine("Move: " + stateMachine.getMoveID());
        write("\n");
    }

    public void showFinalStats() throws IOException {
        showMoveID();
        showBoard();
        String winner = stateMachine.getWinnerString();
        writeLine("Winner: " + winner);
        write("\n");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Menu Functions">
    private void menuMain() throws NumberFormatException, IOException {
        // Start the game
        writeLine("<------- Welcome to Connect 4 ------->");
        write("\n");
        writeLine(" [1] - Play");
        writeLine(" [2] - Load game");
        writeLine(" [3] - Replay");
        writeLine(" [0] - Exit");
        write("\n > ");
        String option = readLine();
        int op = Integer.parseInt(option);

        switch (op) {
            case 1 -> {
                stateMachine.play();
            }
            case 2 -> {
                String fileName = readLine("Please input file location:");
                stateMachine.load(fileName);
            }
            case 3 -> {
                write("\n");
                File[] files = stateMachine.getReplays();
                for (int i = 0; i < files.length; i++) {
                    writeLine("[" + (i + 1) + "] - " + files[i].getPath());
                }
                writeLine("[0] - Manual selection");
                write("\n > ");
                String fIndStr = readLine();
                int fIndex = Integer.parseInt(fIndStr);
                String fileName;
                if (fIndex == 0) {
                    fileName = readLine("Please input file location:");
                } else {
                    fileName = files[fIndex - 1].getPath();
                }
                stateMachine.replay(fileName);
            }
            case 0 -> {
                stateMachine.exit();
            }
            default -> {
                writeLine("\nInvalid option!");
            }
        }
    }

    private void menuConfigGame() throws IOException {
        writeLine("<------- Configure Game ------->");
        write("\n");
        showPlayers();
        writeLine(" [1] - Add human player");
        writeLine(" [2] - Add computer player");
        writeLine(" [3] - Remove player");
        writeLine(" [4] - Start game");
        writeLine(" [0] - Back");
        write("\n > ");
        String option = readLine();
        int op = Integer.parseInt(option);

        switch (op) {
            case 1 -> {
                String playerName = readLine("Please input player name:");
                showDiscs();
                char discColor = readChar("Please select a disc color:");
                stateMachine.addHumanPlayer(playerName, discColor);
            }
            case 2 -> {
                String playerName = readLine("Please input player name:");
                showDiscs();
                char discColor = readChar("Please select a disc color:");
                stateMachine.addComputerPlayer(playerName, discColor);
            }
            case 3 -> {
                String playerName = readLine("Please input player name:");
                stateMachine.removePlayer(playerName);
            }
            case 4 -> {
                stateMachine.startGame();
            }
            case 0 -> {
                stateMachine.exit();
            }
            default -> {
                writeLine("\nInvalid option!");
            }
        }
    }

    private void menuPlayerTurn() throws IOException {
        writeLine("<------- Player '" + stateMachine.getCurrentPlayerName() + "' turn ------->");
        write("\n");
        showCurrentPlayer();
        showMoveID();
        showBoard();
        if (!stateMachine.isCurrentPlayerBot()) {
            writeLine(" [1] - Place disc");
            writeLine(" [2] - Place special disc");
            writeLine(" [3] - Rollback");
            writeLine(" [4] - Save game");
            writeLine(" [0] - Back");
            write("\n > ");
            String option = readLine();
            int op = Integer.parseInt(option);

            switch (op) {
                case 1 -> {
                    String columnStr = readLine("Please input column:");
                    int column = Integer.parseInt(columnStr);
                    stateMachine.placeDisc(column);
                }
                case 2 -> {
                    String columnStr = readLine("Please input column:");
                    int column = Integer.parseInt(columnStr);
                    stateMachine.placeSpecialDisc(column);
                }
                case 3 -> {
                    String rollbackNumberStr = readLine("Please input number of rollbacks:");
                    int rollbackNumber = Integer.parseInt(rollbackNumberStr);
                    stateMachine.rollback(rollbackNumber);
                }
                case 4 -> {
                    String fileName = readLine("Please input file location:");
                    stateMachine.save(fileName);
                }
                case 0 -> {
                    stateMachine.exit();
                }
                default -> {
                    writeLine("\nInvalid option!");
                }
            }
        } else {
            stateMachine.placeDisc(stateMachine.getCurrentPlayerNextMove());
        }
    }

    private void menuPlayMinigame() throws IOException {
        writeLine("<------- Minigame '" + stateMachine.getCurrentPlayerName() + "' ------->");
        write("\n");
        showBoard();
        writeLine(" [1] - Play minigame");
        writeLine(" [2] - Continue Connect 4");
        write("\n > ");
        String option = readLine();
        int op = Integer.parseInt(option);

        switch (op) {
            case 1 -> {
                stateMachine.playMinigame();
            }
            case 2 -> {
                stateMachine.playConnect4();
            }
            default -> {
                writeLine("\nInvalid option!");
            }
        }
    }

    private void menuMinigame() throws IOException {
        stateMachine.startMinigame();
        writeLine("<------- Minigame '" + stateMachine.getCurrentPlayerName() + "' ------->");
        write("\n");
        writeLine("You have " + stateMachine.getMinigameNumSeconds() + " seconds to respond correctly " + stateMachine.getMinigameNumCorrect() + " time(s)!");
        writeLine("Tries: " + stateMachine.getMinigameTries());
        writeLine("Correct: " + stateMachine.getMinigameCorrect());
        writeLine("Wrong: " + stateMachine.getMinigameWrong());
        write("\n");
        writeLine("Problem: '" + stateMachine.getMinigameProblem() + "'");
        write("\n");
        String solution = readLine("Please input the solution:");
        stateMachine.inputSolution(solution);
        stateMachine.endMinigame();
    }

    private void menuGameOver() throws IOException {
        writeLine("<------- Game Over ------->");
        write("\n");
        showFinalStats();
        writeLine(" [1] - Play again");
        writeLine(" [2] - Save game");
        writeLine(" [0] - Exit");
        write("\n > ");
        String option = readLine();
        int op = Integer.parseInt(option);

        switch (op) {
            case 1 -> {
                stateMachine.playAgain();
            }
            case 2 -> {
                String fileName = readLine("Please input file location:");
                stateMachine.save(fileName);
            }
            case 0 -> {
                stateMachine.exit();
            }
            default -> {
                writeLine("\nInvalid option!");
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Helper Functions">
    private char readChar(String message) throws IOException {
        writeLine(message);
        write(" > ");
        return readChar();
    }

    private char readChar() throws IOException {
        String str = readLine();
        if (str.isBlank()) {
            return (char) -1;
        }
        return str.charAt(0);
    }

    private String readLine(String message) throws IOException {
        writeLine(message);
        write(" > ");
        return readLine();
    }

    private String readLine() throws IOException {
        return reader.readLine();
    }

    private void write(String text) throws IOException {
        _write(text, false);
    }

    private void writeLine(String text) throws IOException {
        _write(text, true);
    }

    private void _write(String text, boolean newLine) throws IOException {
        writer.write(text);
        if (newLine) {
            writer.newLine();
        }
        writer.flush();
    }
    //</editor-fold>
}
