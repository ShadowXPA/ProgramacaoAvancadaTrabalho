package pt.isec.a2019112789.connect4s.game.logic.data;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public abstract class Player implements Serializable {

    private final String name;
    private final EDisc disc;
    private int credits;
    private int specialPieces;
    private int minigameCounter;

    protected static final int MOVES_FOR_MINIGAMES = 4;

    protected Player(String name, EDisc disc, int credits, int specialPieces, int minigameCounter) {
        this.name = name;
        this.disc = disc;
        this.credits = credits;
        this.specialPieces = specialPieces;
        this.minigameCounter = minigameCounter;
    }

    public String getName() {
        return name;
    }

    public EDisc getDisc() {
        return disc;
    }

    public int getCredits() {
        return credits;
    }

    public boolean decrementCredits() {
        if (this.credits <= 0) {
            return false;
        }
        this.credits--;
        return true;
    }

    public int getSPCount() {
        return specialPieces;
    }

    protected void setSPCount(int specialPieces) {
        this.specialPieces = specialPieces;
    }

    public int getMinigameCounter() {
        return this.minigameCounter;
    }

    protected void setMinigameCounter(int minigameCounter) {
        this.minigameCounter = minigameCounter;
    }

    public void incrementSP() {
    }

    public void decrementSP() {
    }

    public void incrementMinigameCounter() {
    }

    public void resetMinigameCounter() {
    }

    public boolean isAvailableForMinigame() {
        return false;
    }

    public abstract boolean isBot();

    public abstract int getNextMove(Random rand, int maxCols);

    public abstract Player newInstance();

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.disc);
        hash = 53 * hash + this.credits;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        final Player other = (Player) obj;
        return (Objects.equals(this.name, other.name) || this.disc == other.disc);
    }

    @Override
    public String toString() {
        return "\n Name: " + name + "\n Disc: " + disc.name() + "\n Credits: "
                + credits + "\n Special pieces: " + specialPieces
                + "\n Minigame Counter: " + minigameCounter
                + "\n Is human: " + (!isBot() ? "yes" : "no");
    }
}
