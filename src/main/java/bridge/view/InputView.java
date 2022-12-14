package bridge.view;

/**
 * 사용자로부터 입력을 받는 역할을 한다.
 */
public class InputView {
    private static final InputView instance = new InputView();

    public static InputView getInstance(){
        return instance;
    }
    private InputView() {
    }
    /**
     * 다리의 길이를 입력받는다.
     */
    public int readBridgeSize() {
        return 0;
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
        INPUT_BUDGET("구입금액을 입력해 주세요.");

        private final String message;

        Message(String message) {
            this.message = message;
        }
    }
}
