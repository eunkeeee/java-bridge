package bridge.controller;


import bridge.BridgeMaker;
import bridge.BridgeRandomNumberGenerator;
import bridge.model.bridge.Bridge;
import bridge.model.command.GameCommand;
import bridge.model.game.BridgeGame;
import bridge.model.game.GameVariable;
import bridge.model.status.ApplicationStatus;
import bridge.model.status.GameStatus;
import bridge.model.status.RoundStatus;
import bridge.view.InputView;
import bridge.view.OutputView;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;

    private BridgeGame bridgeGame;
    private GameVariable gameVariable;
    private Bridge bridge;
    private final Map<ApplicationStatus, Supplier<ApplicationStatus>> gameGuide;

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameGuide = new EnumMap<>(ApplicationStatus.class);
        initializeGameGuide();
    }

    private void initializeGameGuide() {
        gameGuide.put(ApplicationStatus.INITIALIZE_APPLICATION, this::initialSetting);
        gameGuide.put(ApplicationStatus.START_GAME, this::startGame);
        gameGuide.put(ApplicationStatus.END_GAME, this::endGame);
    }

    public ApplicationStatus progress(ApplicationStatus applicationStatus) {
        try {
            return gameGuide.get(applicationStatus).get();
        } catch (IllegalArgumentException exception) {
            outputView.printExceptionMessage(exception);
            return applicationStatus;
        }
    }

    private ApplicationStatus initialSetting() {
        outputView.printStartGame();
        setBridge();
        initializeGameVariables();
        return ApplicationStatus.START_GAME;
    }

    private ApplicationStatus startGame() {
        while (!gameVariable.isExitGame()) {
            moveRounds(bridge);
            handleRoundFail();
        }
        return ApplicationStatus.END_GAME;
    }

    private ApplicationStatus endGame() {
        outputView.printResult(gameVariable);
        return ApplicationStatus.APPLICATION_EXIT;
    }


    private void setBridge() {
        BridgeMaker bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
        List<String> bridgeList = bridgeMaker.makeBridge(inputView.readBridgeSize());
        bridge = Bridge.from(bridgeList);
    }

    private void initializeGameVariables() {
        gameVariable = GameVariable.byInitialValue();
        bridgeGame = new BridgeGame(gameVariable);
    }

    private void moveRounds(Bridge bridge) {
        for (int index = 0; index < bridge.getBridgeSize(); index++) {
            RoundStatus roundStatus = moveOneRound(bridge, index);
            if (roundStatus.isRoundFail()) {
                updateGameStatus(GameStatus.GAME_FAIL);
                break;
            }
            if (bridge.isEndOfBridge(index) && roundStatus.isRoundSuccess()) {
                updateGameStatus(GameStatus.GAME_SUCCESS);
            }
        }
    }

    private RoundStatus moveOneRound(Bridge bridge, int index) {
        RoundStatus roundStatus = bridgeGame.move(bridge.getBridgeDirection(index), inputView.readMoving());
        outputView.printMap(gameVariable.getMaps());
        return roundStatus;
    }

    private void handleRoundFail() {
        if (gameVariable.isGameFail()) {
            handleGameCommand(inputView.readGameCommand());
        }
    }

    private void handleGameCommand(GameCommand gameCommand) {
        updateGameStatus(GameStatus.fromGameCommand(gameCommand));
        if (gameVariable.isRetryGame()) {
            bridgeGame.retry();
        }
    }

    private void updateGameStatus(GameStatus gameStatus) {
        gameVariable.updateGameStatus(gameStatus);
    }

}
