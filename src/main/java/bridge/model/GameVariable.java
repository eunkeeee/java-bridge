package bridge.model;

public class GameVariable {
    private int attempts;
    private boolean isSuccessInGame;
    private Diagram diagram;

    public GameVariable() {
        this.attempts = 1;
        this.isSuccessInGame = false;
        this.diagram = new Diagram();
    }

    public void addAttempts() {
        attempts++;
    }

    public void updateDiagram(BridgeSign bridgeSign, RoundStatus roundStatus) {
        diagram.updateDiagram(bridgeSign, roundStatus);
    }

    public String getDiagrams() {
        return diagram.toString();
    }

    public void setIsSuccessInGame() {
        isSuccessInGame = true;
    }

    public void resetDiagrams() {
        diagram = new Diagram();
    }

    public boolean getIsSuccessInGame() {
        return isSuccessInGame;
    }

    public int getAttempts() {
        return attempts;
    }
}
