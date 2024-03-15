package pt.isec.a2019112789.connect4s.game.logic.data;

import java.util.Random;

public class MathMinigame extends Minigame {

    private int number1;
    private int number2;
    private Operator operator;
    private String solution;

    public MathMinigame(Random rand) {
        super(rand, 5);
    }

    @Override
    public void newProblem() {
        number1 = getRandom(1, 99);
        number2 = getRandom(1, 99);
        operator = Operator.values()[getRandom(0, Operator.values().length - 1)];
        solution = "" + operator.calculate(number1, number2);
        setProblem("" + number1 + operator.getChar() + number2);
    }

    @Override
    public boolean evaluateProblem(String problem) {
        boolean correct = problem.equals(solution);
        triedEvaluatingProblem(correct);
        return correct;
    }

    @Override
    public int getNumSeconds() {
        return 30;
    }
}
