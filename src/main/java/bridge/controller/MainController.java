package bridge.controller;

import bridge.BridgeMaker;
import bridge.BridgeRandomNumberGenerator;
import bridge.model.Bridge;
import bridge.model.BridgeSign;
import bridge.model.GameVariable;
import bridge.model.RestartCommand;
import bridge.view.InputView;
import bridge.view.OutputView;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class MainController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<ApplicationStatus, Supplier<ApplicationStatus>> gameGuide;
    private GameVariable gameVariable;

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
        gameGuide.put(ApplicationStatus.INITIALIZE_GAME, this::initializeGame);
        gameGuide.put(ApplicationStatus.GAME_START, this::startGame);
        gameGuide.put(ApplicationStatus.ROUND_END, this::endRound);
        gameGuide.put(ApplicationStatus.GAME_SUCCESS, this::handleGameSuccess);
        gameGuide.put(ApplicationStatus.RESTART_GAME, this::restartGame);
        gameGuide.put(ApplicationStatus.QUIT_GAME, this::quitGame);
    }


    private ApplicationStatus createBridge() {
        outputView.printGameStart();
        int bridgeSize = inputView.readBridgeSize();
        BridgeMaker bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
        Bridge.setBridge(bridgeMaker.makeBridge(bridgeSize));
        System.out.println(Bridge.bridge());
        return ApplicationStatus.INITIALIZE_GAME;
    }

    private ApplicationStatus initializeGame() {
        gameVariable = new GameVariable();
        return ApplicationStatus.GAME_START;
    }

    private ApplicationStatus startGame() {
        for (int index = 0; index < Bridge.size(); index++) {

            // ROUND_START
            BridgeSign bridgeSign = inputView.readMoving();
            gameVariable.updateDiagram(bridgeSign, Bridge.getRoundStatus(index, bridgeSign));
            outputView.printDiagrams(gameVariable.getDiagrams());
            if (Bridge.isRoundEnd(index, bridgeSign)) {
                // ROUND_END
                return ApplicationStatus.ROUND_END;
            }

            // ROUND_CONTINUE

        }

        return ApplicationStatus.GAME_SUCCESS;
    }

    private ApplicationStatus endRound() {
        RestartCommand restartCommand = inputView.readGameCommand();
        if (restartCommand.isQuitGame()) {
            return ApplicationStatus.QUIT_GAME;
        }
        return ApplicationStatus.RESTART_GAME;
    }

    private ApplicationStatus restartGame() {
        gameVariable.addAttempts();
        gameVariable.resetDiagrams();
        return ApplicationStatus.GAME_START;
    }

    private ApplicationStatus quitGame() {
        outputView.printResult(gameVariable);
        return ApplicationStatus.APPLICATION_EXIT;
    }

    private ApplicationStatus handleGameSuccess() {
        gameVariable.setIsSuccessInGame();
        outputView.printResult(gameVariable);
        return ApplicationStatus.APPLICATION_EXIT;
    }

    private enum ApplicationStatus {
        CREATE_BRIDGE,
        INITIALIZE_GAME,
        GAME_START,
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