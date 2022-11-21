package bridge.model;

import java.util.Arrays;

public enum SuccessAndFail {
    SUCCESS("성공", true),
    FAIL("실패", false);

    private final String koreanDisplay;
    private final boolean isSuccess;

    SuccessAndFail(String koreanDisplay, boolean isSuccess) {
        this.koreanDisplay = koreanDisplay;
        this.isSuccess = isSuccess;
    }

    public  String getKoreanDisplay() {
        return koreanDisplay;
    }

    public static boolean isSuccess(SuccessAndFail successAndFail) {
        return successAndFail.isSuccess;
    }

}
