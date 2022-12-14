package bridge.model;

public class GameRepository {
    private static int attempts = 1;
    private static boolean isSuccessInGame = false;
    private static Diagram diagram = new Diagram();

    private GameRepository() {
    }

    public static void addAttempts() {
        attempts++;
    }

    public static void updateDiagram(BridgeSign bridgeSign, RoundStatus roundStatus) {
        diagram.updateDiagram(bridgeSign, roundStatus);
    }

    public static String getDiagrams() {
        return diagram.toString();
    }

    public static void setIsSuccessInGame() {
        isSuccessInGame = true;
    }

    public static void resetDiagrams() {
        diagram = new Diagram();
    }

    public static boolean getIsSuccessInGame() {
        return isSuccessInGame;
    }

    public static int getAttempts() {
        return attempts;
    }
}
