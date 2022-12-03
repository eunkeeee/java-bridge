package bridge.controller;

import bridge.BridgeMaker;
import bridge.BridgeRandomNumberGenerator;
import bridge.model.BridgeGame;
import bridge.model.GameStatus;
import bridge.model.GameVariable;
import bridge.model.RoundStatus;
import bridge.model.bridge.Bridge;
import bridge.model.bridge.BridgeDirection;
import bridge.model.command.GameCommand;
import bridge.view.InputView;
import bridge.view.OutputView;
import java.util.Iterator;
import java.util.List;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;
    private GameStatus gameStatus;
    private BridgeGame bridgeGame;

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameStatus = GameStatus.SETTING_GAME;
    }

    public void play() {
        try {
            // SETTING_GAME
            outputView.printStartGame();

            // CREATING_BRIDGE
            BridgeMaker bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
            List<String> bridgeList = bridgeMaker.makeBridge(inputView.readBridgeSize());
            System.out.println(bridgeList);
            Bridge bridge = Bridge.from(bridgeList);

            // INITIALIZING_GAME_VARIABLE
            GameVariable gameVariable = GameVariable.byInitialValue();

            // START_GAME
            bridgeGame = new BridgeGame(gameVariable);

            Iterator<BridgeDirection> bridgeSignIterator = bridge.getBridgeIterator();
            while (gameStatus.isContinueGame()) {
                RoundStatus roundStatus = bridgeGame.move(bridgeSignIterator.next(),
                        BridgeDirection.from(inputView.readMoving()));
                updateGameStatus(roundStatus);
                outputView.printMap(gameVariable.getMaps());

                if (!bridgeSignIterator.hasNext()) {
                    updateGameStatusToGameSuccess();
                }

                if (gameStatus == GameStatus.ROUND_FAIL) {
                    handleGameCommand(GameCommand.from(inputView.readGameCommand()));
                }
            }

        } catch (IllegalArgumentException exception) {
            outputView.printExceptionMessage(exception);
        }
    }

    private void updateGameStatusToGameSuccess() {
        gameStatus = GameStatus.GAME_SUCCESS;
    }

    private void updateGameStatus(RoundStatus roundStatus) {
        gameStatus = GameStatus.fromRoundStatus(roundStatus);
    }

    private void handleGameCommand(GameCommand gameCommand) {
        gameStatus = GameStatus.fromGameCommand(gameCommand);
        if(gameStatus == GameStatus.RETRY_GAME) {
            bridgeGame.retry();
        }
    }

}
