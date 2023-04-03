package mfc.interfaces;

import mfc.entities.Customer;
import mfc.exceptions.NoMatriculationException;
import mfc.exceptions.ParkingException;

public interface ParkingProcessor {
    boolean useParkingPayOff(Customer user) throws NoMatriculationException, ParkingException;
}
