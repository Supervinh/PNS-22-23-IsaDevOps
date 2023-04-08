package mfc.exceptions;

public class AlreadyExistingPayoffException extends Exception {
    @Override
    public String getMessage() {
        return "This payoff already exist";
    }
}
