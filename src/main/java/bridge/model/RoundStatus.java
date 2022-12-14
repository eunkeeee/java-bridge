package bridge.model;

public enum RoundStatus {
    ROUND_CONTINUE("O"), ROUND_END("X"), SPACE(" ");

    private final String diagram;

    RoundStatus(String diagram) {
        this.diagram = diagram;
    }

    public String getDiagram() {
        return diagram;
    }
}
