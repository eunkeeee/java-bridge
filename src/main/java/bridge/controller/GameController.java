package bridge.controller;

import static bridge.controller.InputController.getBridgeSize;
import static bridge.controller.InputController.getGameCommand;
import static bridge.model.GameCommand.selectedRetry;

import bridge.BridgeMaker;
import bridge.BridgeNumberGenerator;
import bridge.BridgeRandomNumberGenerator;
import bridge.model.Bridge;
import bridge.model.BridgeGame;
import bridge.model.Diagram;
import bridge.model.FinalResult;
import bridge.model.GameCommand;
import bridge.view.InputView;
import bridge.view.OutputView;

public class GameController {
    private static InputView inputView = new InputView();
    private static OutputView outputView = new OutputView();


    public void play() {

        outputView.printStartGame();

        Bridge bridge = createBridge();

        FinalResult finalResult = new FinalResult();
        BridgeGame bridgegame = new BridgeGame(bridge, finalResult);

        do {
            Diagram diagram = new Diagram();
            bridgegame.move(diagram);
            if (finalResult.isSuccess()) {
                outputView.printResult(diagram, finalResult);
            }
            if (!finalResult.isSuccess()) {
                GameCommand gameCommand = getGameCommand();
                if (selectedRetry(gameCommand)) {
                    finalResult.addAttempts();
                }
                if (!selectedRetry(gameCommand)) {
                    finalResult.quit();
                    outputView.printResult(diagram, finalResult);
                }
            }
        } while (bridgegame.retry());
    }

    private static Bridge createBridge() {
        BridgeNumberGenerator bridgeNumberGenerator = new BridgeRandomNumberGenerator();
        BridgeMaker bridgeMaker = new BridgeMaker(bridgeNumberGenerator);
        Bridge bridge = new Bridge(bridgeMaker.makeBridge(getBridgeSize()));
        return bridge;
    }
}
