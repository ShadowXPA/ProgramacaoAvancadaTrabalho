package pt.isec.a2019112789.connect4s.game.ui.gui;

public class SelectMinigameController extends Controller {

    @Override
    public void show() {
        super.show();
        app.getStateMachine().playMinigame();
    }
}
