package mfc.exceptions;

public class AlreadyExistingSurveyException extends Exception {
    @Override
    public String getMessage() {
        return "This survey already exist";
    }
}
