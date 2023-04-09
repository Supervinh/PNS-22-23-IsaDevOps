package mfc.exceptions;

public class InvalidAnswerException extends Exception {
    @Override
    public String getMessage() {
        return "This is not a valid answer to the question";
    }
}
