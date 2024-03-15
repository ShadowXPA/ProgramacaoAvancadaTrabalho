package pt.isec.a2019112789.connect4s.game.logic.states;

import pt.isec.a2019112789.connect4s.game.logic.data.GameData;
import pt.isec.a2019112789.connect4s.game.logic.data.Minigame;

public abstract class MinigameAbstractState extends State {

    private final Minigame minigame;

    public MinigameAbstractState(Minigame minigame, GameData gameData) {
        super(gameData);
        gameData.setMinigame(minigame);
        this.minigame = minigame;
    }

    public Minigame getMinigame() {
        return minigame;
    }

    public long elapsedTime() {
        return minigame.getElapsedTime();
    }

    public boolean minigameTimeout() {
        long elapsed = elapsedTime();
        return (elapsed > (getMinigame().getNumSeconds() * 1000));
    }

    protected long getStartTime() {
        return minigame.getStartTime();
    }

    protected long getEndTime() {
        return minigame.getEndTime();
    }

    protected void setStartTime(long startTime) {
        minigame.setStartTime(startTime);
    }

    protected void setEndTime(long endTime) {
        minigame.setEndTime(endTime);
    }

    @Override
    public IState endMinigame() {
        setEndTime(System.currentTimeMillis());
        GameData gd = getGameData();
        if (minigameTimeout()) {
            gd.addMessageLog("Player timed out!");
            gd.addMessageLog("Player tried " + getMinigame().getTries() + " time(s).");
            gd.playerLostMinigame();
            return new MinigameAvailabilityState(gd);
        }
        if (getMinigame().isProblemSolved()) {
            gd.addMessageLog("Player tried " + getMinigame().getTries() + " time(s).");
            gd.playerWonMinigame();
            return new PlayerTurnState(gd);
        }
        return this;
    }
}
