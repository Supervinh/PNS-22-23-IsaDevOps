package mfc.exceptions;

public class VFPExpiredException extends Exception {

    @Override
    public String getMessage() {
        return "VFP expired";
    }
}
