package mfc.interfaces;

import mfc.exceptions.NoMatriculationException;
import mfc.exceptions.ParkingException;
import mfc.entities.Customer;

public interface ParkingProcessor {
    boolean useParkingPayOff(Customer user) throws NoMatriculationException, ParkingException;
}
