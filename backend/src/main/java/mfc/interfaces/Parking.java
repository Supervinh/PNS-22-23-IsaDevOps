package mfc.interfaces;

import mfc.exceptions.ParkingException;

public interface Parking {
    boolean park(String matriculation) throws ParkingException;

}
