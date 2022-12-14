package bridge.model;

import bridge.util.ExceptionMessage;
import java.util.Arrays;

public enum RestartCommand {

    RESTART_GAME("R"),
    QUIT_GAME("Q");

    private final String command;

    RestartCommand(String command) {
        this.command = command;
    }

    public static RestartCommand from(String command) {
        return Arrays.stream(RestartCommand.values())
                .filter(option -> option.command.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_SUCH_RESTART_COMMAND.getMessage()));
    }

    public boolean isQuitGame() {
        return this == QUIT_GAME;
    }
}
