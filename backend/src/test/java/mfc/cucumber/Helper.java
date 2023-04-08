package mfc.cucumber;

public class Helper {

    private static String exception;

    public static void resetException() {
        exception = "";
    }

    public static String getException() {
        return exception;
    }

    public static void setException(String exception) {
        Helper.exception = exception;
    }
}
