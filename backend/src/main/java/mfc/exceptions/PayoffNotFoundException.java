package mfc.exceptions;

public class PayoffNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "No payoff with this name";
    }
}
