package interfaces;

import interfaces.Exceptions.ParkingException;

public interface Parking {
    boolean park(String matriculation) throws ParkingException;

}
