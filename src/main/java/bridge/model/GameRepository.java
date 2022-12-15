package bridge.model;

public class GameRepository {
    private int attempts = 1;
    private boolean isSuccessInGame = false;
    private Diagram diagram = new Diagram();

    public GameRepository() {
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
