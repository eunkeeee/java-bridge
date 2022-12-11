package bridge;

import bridge.controller.GameController;
import bridge.model.status.ApplicationStatus;
import bridge.view.InputView;
import bridge.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        ApplicationStatus applicationStatus = ApplicationStatus.INITIALIZE_APPLICATION;
        GameController gameController = new GameController(inputView, outputView);

        do {
            applicationStatus = gameController.progress(applicationStatus);
        } while (applicationStatus.isPlayable());
    }
}
