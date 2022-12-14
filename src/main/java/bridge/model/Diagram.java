package bridge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Diagram {

    private final List<String> upDiagram = new ArrayList<>();
    private final List<String> downDiagram = new ArrayList<>();

    public void updateDiagram(BridgeSign bridgeSign, RoundStatus roundStatus) {
        if (bridgeSign == BridgeSign.UP) {
            upDiagram.add(roundStatus.getDiagram());
            downDiagram.add(RoundStatus.SPACE.getDiagram());
        }
        if (bridgeSign == BridgeSign.DOWN) {
            upDiagram.add(RoundStatus.SPACE.getDiagram());
            downDiagram.add(roundStatus.getDiagram());
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(formatDiagram(upDiagram));
        result.append(formatDiagram(downDiagram));

        return result.toString();
    }

    private String formatDiagram(List<String> diagrams) {
        StringJoiner stringJoiner = new StringJoiner(" | ", "[ ", " ]\n");
        for (String diagram : diagrams) {
            stringJoiner.add(diagram);
        }
        return stringJoiner.toString();
    }
}
