package bridge.controller;

import bridge.view.InputView;
import bridge.view.OutputView;
import java.util.EnumMap;
import java.util.Map;

public class MainController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<ApplicationStatus, Runnable> controllers;

    private enum ApplicationStatus {
        CREATE_BRIDGE,
        GAME_START,
        INITIALIZE_GAME,
        ROUND_START,
        ROUND_CONTINUE,
        ROUND_END,
        SELECT_RESTART,
        RESTART_GAME,
        QUIT_GAME,
        GAME_SUCCESS,
        APPLICATION_EXIT
    }

    public MainController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.controllers = new EnumMap<>(ApplicationStatus.class);
        initializeControllers();
    }

    private void initializeControllers() {
        controllers.put(ApplicationStatus.CREATE_BRIDGE, this::createBridge);
    }

    public void service() {
    }

    private void createBridge() {
    }


    public void progress(ApplicationStatus applicationStatus) {
        try {
            controllers.get(applicationStatus).run();
        } catch (IllegalArgumentException exception) {
            outputView.printExceptionMessage(exception);
        }
    }

}