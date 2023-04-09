package mfc.exceptions;

public class NoCreditCardException extends Exception {

    @Override
    public String getMessage() {
        return "No credit card";
    }
}
