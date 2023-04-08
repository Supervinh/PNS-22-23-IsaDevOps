package mfc.exceptions;

public class SurveyNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "No survey with this name";
    }
}
