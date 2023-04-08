package mfc.exceptions;

public class AlreadyRegisteredStoreException extends Exception {

    @Override
    public String getMessage() {
        return "Store already registered";
    }
}
