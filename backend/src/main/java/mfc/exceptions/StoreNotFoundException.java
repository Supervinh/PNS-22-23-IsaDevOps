package mfc.exceptions;

public class StoreNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "No store with this name";
    }
}
