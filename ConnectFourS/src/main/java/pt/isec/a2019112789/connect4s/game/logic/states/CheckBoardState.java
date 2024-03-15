package pt.isec.a2019112789.connect4s.game.logic.states;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;
import pt.isec.a2019112789.connect4s.game.logic.data.GameData;

public class CheckBoardState extends State {

    private static final SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public CheckBoardState(GameData gameData) {
        super(gameData);
    }

    @Override
    public IState checkBoard() {
        GameData gd = getGameData();
        if (gd.getWinner() != null) {
            StringJoiner sj = new StringJoiner("_vs_");
            gd.getPlayers().forEach(p -> sj.add(p.getName()));
            autosave(sDF.format(new Date()) + "_" + sj.toString() + ".pa");
            return new GameOverState(gd);
        }
        return new MinigameAvailabilityState(gd);
    }

    private void autosave(String fileName) {
        File dir = new File("Replays");
        if (!dir.exists()) {
            dir.mkdir();
        }
        this.save(dir.getPath() + File.separator + fileName);
        File[] listFiles = dir.listFiles((file, name) -> {
            return name.endsWith(".pa");
        });
        if (listFiles.length > 5) {
            Arrays.sort(listFiles);
            listFiles[0].delete();
        }
    }
}
