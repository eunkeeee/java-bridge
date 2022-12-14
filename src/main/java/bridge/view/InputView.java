package bridge.view;

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
    public String readMoving() {
        return null;
    }

    /**
     * 사용자가 게임을 다시 시도할지 종료할지 여부를 입력받는다.
     */
    public String readGameCommand() {
        return null;
    }

    private enum Message {
        INPUT_BRIDGE_SIZE("다리의 길이를 입력해주세요.");

        private final String message;

        Message(String message) {
            this.message = message;
        }
    }
}
