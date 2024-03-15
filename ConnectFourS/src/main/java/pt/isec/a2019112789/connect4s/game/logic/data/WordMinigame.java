package pt.isec.a2019112789.connect4s.game.logic.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

public class WordMinigame extends Minigame {

    private final ArrayList<String> words;

    private static final String FILE_PATH = "MOCK_DATA.txt";

    public WordMinigame(Random rand) throws FileNotFoundException, IOException {
        super(rand, 1);
        words = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + FILE_PATH + "' does not exist.");
        }
        try ( FileInputStream fIS = new FileInputStream(file)) {
            String str = new String(fIS.readAllBytes());
            String[] fileWords = str.split(System.lineSeparator());
            words.addAll(Arrays.asList(fileWords));
        }
    }

    @Override
    public void newProblem() {
        StringJoiner sj = new StringJoiner(" ");
        for (int i = 0; i < 5; i++) {
            sj.add(words.get(getRandom(0, (words.size() - 1))));
        }
        setProblem(sj.toString());
    }

    @Override
    public boolean evaluateProblem(String problem) {
        boolean correct = problem.equals(getProblem());
        triedEvaluatingProblem(correct);
        return correct;
    }

    @Override
    public int getNumSeconds() {
        int temp = 0;
        String problem = getProblem();
        if (problem != null) {
            temp = problem.length() / 2;
        }
        return temp;
    }
}
