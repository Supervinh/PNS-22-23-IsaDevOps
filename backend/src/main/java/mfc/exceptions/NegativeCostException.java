package mfc.exceptions;

public class NegativeCostException extends Exception {
    @Override
    public String getMessage() {
        return "Negative costs are not allowed";
    }
}
