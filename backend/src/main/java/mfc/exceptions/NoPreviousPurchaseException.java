package mfc.exceptions;

public class NoPreviousPurchaseException extends Exception {

    @Override
    public String getMessage() {
        return "No previous purchase in this store";
    }
}
