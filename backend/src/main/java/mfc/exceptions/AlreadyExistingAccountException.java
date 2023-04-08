package mfc.exceptions;

public class AlreadyExistingAccountException extends Exception {
    @Override
    public String getMessage() {
        return "This account already exist";
    }
}
