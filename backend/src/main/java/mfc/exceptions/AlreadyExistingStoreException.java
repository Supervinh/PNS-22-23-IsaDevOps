package mfc.exceptions;

public class AlreadyExistingStoreException extends Exception {
    @Override
    public String getMessage() {
        return "This store already exist";
    }
}
