package mfc.exceptions;

public class AccountNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Account not found";
    }
}
