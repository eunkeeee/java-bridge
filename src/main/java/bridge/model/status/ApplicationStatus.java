package bridge.model.status;

public enum ApplicationStatus {

    INITIALIZE_APPLICATION,
    START_GAME,
    END_GAME,
    APPLICATION_EXIT;

    public boolean isPlayable() {
        return this != APPLICATION_EXIT;
    }
}
