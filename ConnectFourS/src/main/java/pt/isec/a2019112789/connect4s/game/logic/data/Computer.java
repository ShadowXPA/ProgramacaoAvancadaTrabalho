package pt.isec.a2019112789.connect4s.game.logic.data;

import java.util.Random;

public final class Computer extends Player {

    public Computer(String name, EDisc disc) {
        super(name, disc, 0, 0, 0);
    }

    public Computer(Computer c) {
        super(c.getName(), c.getDisc(), c.getCredits(), c.getSPCount(), c.getMinigameCounter());
    }

    @Override
    public boolean isBot() {
        return true;
    }

    @Override
    public int getNextMove(Random rand, int maxCols) {
        return rand.nextInt(maxCols) + 1;
    }

    @Override
    public Player newInstance() {
        return new Computer(this);
    }
}
