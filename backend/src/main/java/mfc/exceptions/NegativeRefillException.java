package mfc.exceptions;

public class NegativeRefillException extends Exception {
    @Override
    public String getMessage() {
        return "You can't refill with a negative amount";
    }
}
