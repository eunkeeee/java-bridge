package bridge.util;

public enum ExceptionMessage {

    INVALID_NOT_NUMERIC("자연수만 입력 가능합니다."),
    INVALID_OUT_OF_INT_RANGE("입력 범위를 초과하였습니다."),
    NO_SUCH_BRIDGE_SIGN("해당하는 기호가 존재하지 않습니다."),
    NO_SUCH_BRIDGE_NUMBER("해당하는 번호가 존재하지 않습니다."),
    NO_SUCH_RESTART_COMMAND("R, Q 중 입력해 주세요.");

    public static final String BASE_MESSAGE = "[ERROR] %s";
    private final String message;

    ExceptionMessage(String message, Object... replaces) {
        this.message = String.format(BASE_MESSAGE, String.format(message, replaces));
    }

    public String getMessage() {
        return message;
    }
}