package mfc.interfaces.exceptions;

public class InsufficientBalanceException extends Exception {
    @Override
    public String getMessage() {
        return "Error. Not enough money to purchase.";
    }
}
