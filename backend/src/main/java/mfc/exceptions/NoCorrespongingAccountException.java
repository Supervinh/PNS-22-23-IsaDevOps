package mfc.exceptions;

public class NoCorrespongingAccountException extends Exception {

    @Override
    public String getMessage() {
        return "No corresponding account";
    }
}
