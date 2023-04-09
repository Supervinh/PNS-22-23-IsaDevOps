package mfc.exceptions;

public class NegativePointCostException extends Exception {
    @Override
    public String getMessage() {
        return "Negative point costs are not allowed";
    }
}
