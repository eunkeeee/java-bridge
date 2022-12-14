package bridge.view;

import bridge.model.BridgeSign;
import bridge.model.RestartCommand;
import bridge.util.Util;
import bridge.util.validator.BridgeSizeValidator;
import camp.nextstep.edu.missionutils.Console;

/**
 * 사용자로부터 입력을 받는 역할을 한다.
 */
public class InputView {
    private static final InputView instance = new InputView();

    public static InputView getInstance() {
        return instance;
    }

    private InputView() {
    }

    /**
     * 다리의 길이를 입력받는다.
     */
    public int readBridgeSize() {
        try {
            System.out.println(Message.INPUT_BRIDGE_SIZE.message);
            String bridgeSize = Util.removeSpace(Console.readLine());
            new BridgeSizeValidator().validate(bridgeSize);
            return Integer.parseInt(bridgeSize);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return readBridgeSize();
        }
    }

    /**
     * 사용자가 이동할 칸을 입력받는다.
     */
    public BridgeSign readMoving() {
        try {
            System.out.println(Message.INPUT_MOVING.message);
            return BridgeSign.from(Util.removeSpace(Console.readLine()));
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return readMoving();
        }
    }

    /**
     * 사용자가 게임을 다시 시도할지 종료할지 여부를 입력받는다.
     */
    public RestartCommand readGameCommand() {
        try {
            System.out.println(Message.INPUT_RESTART_COMMAND.message);
            return RestartCommand.from(Util.removeSpace(Console.readLine()));
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return readGameCommand();
        }
    }

    private enum Message {
        INPUT_BRIDGE_SIZE("다리의 길이를 입력해주세요."),
        INPUT_MOVING("이동할 칸을 선택해주세요. (위: U, 아래: D)"),
        INPUT_RESTART_COMMAND("게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)");

        private final String message;

        Message(String message) {
            this.message = message;
        }
    }
}
