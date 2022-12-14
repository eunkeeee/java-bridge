package bridge.model;

public enum ApplicationStatus {
    CREATE_BRIDGE,
    INITIALIZE_GAME,
    GAME_START,
    ROUND_START,
    ROUND_CONTINUE,
    ROUND_END,
    SELECT_RESTART,
    RESTART_GAME,
    QUIT_GAME,
    GAME_SUCCESS,
    APPLICATION_EXIT;

    public boolean playable() {
        return this != APPLICATION_EXIT;
    }
}