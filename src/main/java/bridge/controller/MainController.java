package bridge.controller;

import bridge.BridgeMaker;
import bridge.BridgeRandomNumberGenerator;
import bridge.view.InputView;
import bridge.view.OutputView;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class MainController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<ApplicationStatus, Supplier<ApplicationStatus>> gameGuide;

    public MainController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameGuide = new EnumMap<>(ApplicationStatus.class);
        initializeGameGuide();
    }

    public void service() {
        ApplicationStatus applicationStatus = progress(ApplicationStatus.CREATE_BRIDGE);
        while (applicationStatus.playable()) {
            applicationStatus = progress(applicationStatus);
        }
    }

    public ApplicationStatus progress(ApplicationStatus applicationStatus) {
        try {
            return gameGuide.get(applicationStatus).get();
        } catch (NullPointerException exception) {
            return ApplicationStatus.APPLICATION_EXIT;
        }
    }

    private void initializeGameGuide() {
        gameGuide.put(ApplicationStatus.CREATE_BRIDGE, this::createBridge);
        gameGuide.put(ApplicationStatus.GAME_START, this::startGame);
    }

    private ApplicationStatus createBridge() {
        outputView.printGameStart();
        int bridgeSize = inputView.readBridgeSize();

        BridgeMaker bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
        System.out.println(bridgeMaker.makeBridge(bridgeSize));
        return ApplicationStatus.GAME_START;
    }

    private ApplicationStatus startGame() {
        return ApplicationStatus.APPLICATION_EXIT;
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