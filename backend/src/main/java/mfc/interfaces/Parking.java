package mfc.interfaces;

import mfc.interfaces.exceptions.ParkingException;

public interface Parking {
    boolean park(String matriculation) throws ParkingException;

}
