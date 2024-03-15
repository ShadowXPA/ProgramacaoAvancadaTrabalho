package pt.isec.a2019112789.connect4s.game.logic;

import pt.isec.a2019112789.connect4s.game.logic.states.*;

public enum EState {
    MainMenu("MainMenu", MainMenuState.class),
    ConfigureGame("ConfigureGame", ConfigureGameState.class),
    Minigame("Minigame", MinigameState.class),
    MathMinigame("MinigameImpl", MathGameState.class),
    WordMinigame("MinigameImpl", WordGameState.class),
    PlayerTurn("PlayerTurn", PlayerTurnState.class),
    Replay("Replay", ReplayState.class),
    GameOver("GameOver", GameOverState.class),
    MinigameAvailability("MinigameAvailability", MinigameAvailabilityState.class),
    SelectMinigame("SelectMinigame", SelectMinigameState.class),
    CheckBoard("CheckBoard", CheckBoardState.class);

    private final String name;
    private final Class<?> stateClass;

    private EState(String s, Class<?> stateClass) {
        this.name = s;
        this.stateClass = stateClass;
    }

    public String getName() {
        return this.toString();
    }

    public Class<?> getStateClass() {
        return this.stateClass;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
