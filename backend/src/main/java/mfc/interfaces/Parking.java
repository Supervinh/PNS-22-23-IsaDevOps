package mfc.interfaces;

import mfc.interfaces.Exceptions.ParkingException;

public interface Parking {
    boolean park(String matriculation) throws ParkingException;

}
