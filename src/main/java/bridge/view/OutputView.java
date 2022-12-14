package bridge.view;

import bridge.model.GameVariable;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {
    private static final OutputView instance = new OutputView();

    public static OutputView getInstance() {
        return instance;
    }

    private OutputView() {
    }

    public void printGameStart() {
        System.out.println(Message.OUTPUT_GAME_START.message);
    }

    public void printExceptionMessage(Exception exception) {
        System.out.println(exception.getMessage());
    }

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap() {
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult(GameVariable gameVariable) {
        System.out.printf(Message.OUTPUT_FINAL_RESULT.message,
                gameVariable.getDiagrams(),
                Success.returnDisplay(gameVariable.getIsSuccessInGame()),
                gameVariable.getAttempts());
    }

    public void printDiagrams(String diagrams) {
        System.out.println(diagrams);
    }

    private enum Message {
        OUTPUT_GAME_START("다리 건너기 게임을 시작합니다.\n"),
        OUTPUT_FINAL_RESULT("최종 게임 결과\n"
                + "%s\n"
                + "게임 성공 여부: %s\n"
                + "총 시도한 횟수: %d");

        private final String message;

        Message(String message) {
            this.message = message;
        }
    }

    private enum Success {
        SUCCESS_GAME("성공"), FAIL_GAME("실패");

        private final String display;

        Success(String display) {
            this.display = display;
        }

        static String returnDisplay(boolean isSuccessInGame) {
            if (isSuccessInGame) {
                return SUCCESS_GAME.display;
            }
            return FAIL_GAME.display;
        }

    }

}
