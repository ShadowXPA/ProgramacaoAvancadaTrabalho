package pt.isec.a2019112789.connect4s.game.ui.gui;

public class MinigameAvailabilityController extends Controller {

    @Override
    public void show() {
        super.show();
        app.getStateMachine().checkMinigameAvailability();
    }
}
