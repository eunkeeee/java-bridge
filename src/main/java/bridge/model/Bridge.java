package bridge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bridge {
    private static final List<BridgeSign> bridge = new ArrayList<>();

    private Bridge() {
    }

    public static void setBridge(List<String> newBridge) {
        bridge.addAll(convertToBridgeSign(newBridge));
    }

    private static List<BridgeSign> convertToBridgeSign(List<String> bridge) {
        return bridge.stream().map(BridgeSign::from).collect(Collectors.toList());
    }
}
