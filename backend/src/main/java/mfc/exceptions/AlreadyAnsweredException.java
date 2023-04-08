package mfc.exceptions;

public class AlreadyAnsweredException extends Exception {
    @Override
    public String getMessage() {
        return "You have already answered this question";
    }
}
