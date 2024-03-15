package pt.isec.a2019112789.connect4s.game.logic.data;

import java.util.Random;

public abstract class Minigame {

    private String problem;
    private int tries;
    private int correct;
    private final int numCorrect;
    private final Random rand;
    private long startTime;
    private long endTime;

    public Minigame(Random rand, int numCorrect) {
        this.problem = null;
        this.tries = 0;
        this.correct = 0;
        this.startTime = 0;
        this.endTime = 0;
        this.numCorrect = numCorrect;
        this.rand = rand;
    }

    public String getProblem() {
        return this.problem;
    }

    protected void setProblem(String problem) {
        this.problem = problem;
    }

    protected int getRandom(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
        this.endTime = this.startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getElapsedTime() {
        return (endTime - startTime);
    }

    public long getElapsedSeconds() {
        return getElapsedTime() / 1000;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public boolean isProblemSolved() {
        return getNumCorrect() == getCorrect();
    }

    public abstract void newProblem();

    public abstract boolean evaluateProblem(String problem);

    public abstract int getNumSeconds();

    public int getTries() {
        return tries;
    }

    public int getCorrect() {
        return correct;
    }

    public int getWrong() {
        return getTries() - getCorrect();
    }

    protected void incrementTries() {
        this.tries++;
    }

    protected void incrementCorrect() {
        this.correct++;
    }

    protected void triedEvaluatingProblem(boolean correct) {
        incrementTries();
        if (correct) {
            incrementCorrect();
        }
    }
}
