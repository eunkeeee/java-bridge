package bridge.model;

public class GameVariable {
    public int attempts;
    public boolean isSuccessInGame;

    public GameVariable() {
        this.attempts = 1;
        this.isSuccessInGame = false;
    }

    public void addAttempts() {
        attempts++;
    }

    public void setIsSuccessInGame() {
        isSuccessInGame = true;
    }
}
