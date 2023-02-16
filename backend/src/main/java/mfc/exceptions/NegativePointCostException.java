package mfc.exceptions;

public class NegativePointCostException extends Exception {
    @Override
    public String getMessage() {
        return "Error. Cannot add negative points.";
    }
}
