package bridge.model;

import java.util.Arrays;

public enum MainOption {

    ORDER_REGISTRATION("1"),
    PAYMENT("2"),
    APPLICATION_EXIT("3");

    private final String command;

    MainOption(String command) {
        this.command = command;
    }

    public boolean isPlayable() {
        return this != APPLICATION_EXIT;
    }

    public static MainOption from(String command) {
        return Arrays.stream(MainOption.values())
                .filter(option -> option.command.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException());
    }

    //  NO_MAIN_OPTION("해당 메인 옵션이 존재하지 않습니다."),

}