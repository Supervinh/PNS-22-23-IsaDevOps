package mfc.exceptions;

public class PaymentException extends Exception {

    @Override
    public String getMessage() {
        return "Payment service isn't available";
    }
}
