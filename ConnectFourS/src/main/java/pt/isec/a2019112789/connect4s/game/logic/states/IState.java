package pt.isec.a2019112789.connect4s.game.logic.states;

import pt.isec.a2019112789.connect4s.game.logic.data.GameData;

public interface IState {

    GameData getGameData();

    IState play();

    IState exit();

    IState load(String fileName);

    IState replay(String fileName);

    IState configGame();

    IState checkMinigameAvailability();

    IState checkBoard();

    IState addHumanPlayer(String playerName, char playerColor);

    IState addComputerPlayer(String playerName, char playerColor);

    IState removePlayer(String playerName);

    IState startGame();

    IState save(String fileName);

    IState placeDisc(int column);

    IState placeSpecialDisc(int column);

    IState rollback(int numRollbacks);

    IState playMinigame();

    IState startMinigame();

    IState endMinigame();

    IState inputSolution(String solution);

    IState playConnect4();

    IState playAgain();

    IState replayStep();
}
