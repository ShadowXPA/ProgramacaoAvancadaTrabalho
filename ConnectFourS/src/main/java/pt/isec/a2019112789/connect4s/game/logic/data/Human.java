package pt.isec.a2019112789.connect4s.game.logic.data;

import java.util.Random;

public final class Human extends Player {

    public Human(String name, EDisc disc) {
        this(name, disc, 5, 0, 0);
    }

    public Human(String name, EDisc disc, int credits, int spCount, int minigameCounter) {
        super(name, disc, credits, spCount, minigameCounter);
    }

    public Human(Human h) {
        super(h.getName(), h.getDisc(), h.getCredits(), h.getSPCount(), h.getMinigameCounter());
    }

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public boolean isAvailableForMinigame() {
        return (getMinigameCounter() % Player.MOVES_FOR_MINIGAMES == 0);
    }

    @Override
    public void incrementMinigameCounter() {
        setMinigameCounter(getMinigameCounter() + 1);
    }

    @Override
    public void resetMinigameCounter() {
        setMinigameCounter(0);
    }

    @Override
    public void incrementSP() {
        setSPCount(getSPCount() + 1);
    }

    @Override
    public void decrementSP() {
        setSPCount(getSPCount() - 1);
    }

    @Override
    public int getNextMove(Random rand, int maxCols) {
        return -1;
    }

    @Override
    public Player newInstance() {
        return new Human(this);
    }
}
