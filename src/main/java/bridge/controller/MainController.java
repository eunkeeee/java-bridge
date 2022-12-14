package bridge.controller;

import bridge.BridgeMaker;
import bridge.BridgeRandomNumberGenerator;
import bridge.model.ApplicationStatus;
import bridge.model.Bridge;
import bridge.model.BridgeGame;
import bridge.model.BridgeSign;
import bridge.model.GameRepository;
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
    private BridgeGame bridgeGame;

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
        bridgeGame = new BridgeGame();
        return ApplicationStatus.GAME_START;
    }

    private ApplicationStatus startGame() {
        for (int index = 0; index < Bridge.size(); index++) {
            BridgeSign bridgeSign = inputView.readMoving();
            bridgeGame.move(index, bridgeSign);
            outputView.printDiagrams(GameRepository.getDiagrams());
            if (Bridge.isRoundEnd(index, bridgeSign)) {
                return ApplicationStatus.ROUND_END;
            }
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
        return bridgeGame.retry();
    }

    private ApplicationStatus quitGame() {
        outputView.printResult();
        return ApplicationStatus.APPLICATION_EXIT;
    }

    private ApplicationStatus handleGameSuccess() {
        GameRepository.setIsSuccessInGame();
        outputView.printResult();
        return ApplicationStatus.APPLICATION_EXIT;
    }

}