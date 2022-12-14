package bridge.model;

import bridge.util.ExceptionMessage;
import java.util.Arrays;

public enum BridgeSign {
    UP("U", 1), DOWN("D", 0);

    private final String sign;
    private final int number;

    BridgeSign(String sign, int number) {
        this.sign = sign;
        this.number = number;
    }

    public static BridgeSign from(int number) {
        return Arrays.stream(BridgeSign.values())
                .filter(element -> element.number == number)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_SUCH_BRIDGE_NUMBER.getMessage()));
    }

    public static BridgeSign from(String sign) {
        return Arrays.stream(BridgeSign.values())
                .filter(element -> element.sign.equals(sign))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_SUCH_BRIDGE_SIGN.getMessage()));
    }

    public static String numberToSign(int number) {
        return from(number).sign;
    }

}
