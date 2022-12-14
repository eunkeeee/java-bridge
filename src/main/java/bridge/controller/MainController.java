package bridge.controller;

import bridge.view.InputView;
import bridge.view.OutputView;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class MainController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<ApplicationStatus, Supplier<ApplicationStatus>> controllers;

    public MainController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.controllers = new EnumMap<>(ApplicationStatus.class);
        initializeControllers();
    }

    public void service() {
        ApplicationStatus applicationStatus = progress(ApplicationStatus.CREATE_BRIDGE);
        while (applicationStatus.playable()) {
            applicationStatus = progress(applicationStatus);
        }
    }

    public ApplicationStatus progress(ApplicationStatus applicationStatus) {
        try {
            return controllers.get(applicationStatus).get();
        } catch (IllegalArgumentException exception) {
            outputView.printExceptionMessage(exception);
            return applicationStatus;
        } catch (NullPointerException exception) {
            outputView.printExceptionMessage(exception);
            return null;
        }
    }

    private void initializeControllers() {
        controllers.put(ApplicationStatus.CREATE_BRIDGE, this::createBridge);
    }


    private ApplicationStatus createBridge() {
        int bridgeSize = inputView.readBridgeSize();
        System.out.println(bridgeSize);

        return ApplicationStatus.GAME_START;
    }

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
        APPLICATION_EXIT;

        public boolean playable() {
            return this != APPLICATION_EXIT;
        }
    }

}