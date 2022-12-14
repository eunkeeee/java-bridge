package bridge.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Bridge {
    private static final List<BridgeSign> bridge = new ArrayList<>();

    private Bridge() {
    }

    public static List<BridgeSign> bridge() {
        return Collections.unmodifiableList(bridge);
    }

    public static void setBridge(List<String> newBridge) {
        bridge.addAll(convertToBridgeSign(newBridge));
    }

    private static List<BridgeSign> convertToBridgeSign(List<String> bridge) {
        return bridge.stream().map(BridgeSign::from).collect(Collectors.toList());
    }

    public static int size() {
        return bridge.size();
    }

    public static RoundStatus getRoundStatus(int index, BridgeSign bridgeSign) {
        if (isRoundEnd(index, bridgeSign)) {
            return RoundStatus.ROUND_END;
        }
        return RoundStatus.ROUND_CONTINUE;
    }

    public static boolean isRoundEnd(int index, BridgeSign bridgeSign) {
        return bridgeSign != bridge.get(index);
    }

}
