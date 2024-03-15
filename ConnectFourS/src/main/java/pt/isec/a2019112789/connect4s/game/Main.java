package pt.isec.a2019112789.connect4s.game;

import pt.isec.a2019112789.connect4s.game.ui.gui.Connect4App;
import pt.isec.a2019112789.connect4s.game.ui.text.Connect4TextUI;

public class Main {

    public static void main(String[] args) {
        try {
            String mode = "gui";

            if (args.length == 1) {
                mode = args[0];
            }

            switch (mode.toLowerCase()) {
                case "text" -> {
                    Connect4TextUI ui = new Connect4TextUI(System.out, System.in);
                    ui.initialize();
                    ui.run();
                }
                case "gui" ->
                    Connect4App.main(args);
                default ->
                    System.out.println("Invalid mode!");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
