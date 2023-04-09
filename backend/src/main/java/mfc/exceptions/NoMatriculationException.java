package mfc.exceptions;

public class NoMatriculationException extends Exception {

    @Override
    public String getMessage() {
        return "No matriculation";
    }
}
