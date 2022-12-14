package bridge;

import bridge.controller.MainController;
import bridge.view.InputView;
import bridge.view.OutputView;

public class Application {
    public static void main(String[] args) {
        MainController mainController = new MainController(InputView.getInstance(), OutputView.getInstance());
        mainController.service();
    }
}