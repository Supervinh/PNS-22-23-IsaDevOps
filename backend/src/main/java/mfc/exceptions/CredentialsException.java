package mfc.exceptions;

public class CredentialsException extends Exception {
    @Override
    public String getMessage() {
        return "This mail/password combination doesn't correspond to this account";
    }
}
