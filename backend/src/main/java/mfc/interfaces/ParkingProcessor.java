package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.interfaces.exceptions.NoMatriculationException;
import mfc.interfaces.exceptions.ParkingException;

public interface ParkingProcessor {
    boolean useParkingPayOff(Customer user) throws NoMatriculationException, ParkingException;
}
