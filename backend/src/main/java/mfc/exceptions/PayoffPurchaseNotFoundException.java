package mfc.exceptions;

public class PayoffPurchaseNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "No payoff purchase found with that name";
    }
}
