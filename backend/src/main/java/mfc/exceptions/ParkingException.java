package mfc.exceptions;

public class ParkingException extends Exception {
    @Override
    public String getMessage() {
        return "Parking service isn't available";
    }
}
