package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.exceptions.NoMatriculationException;
import mfc.exceptions.ParkingException;

public interface ParkingProcessor {
    boolean useParkingPayOff(Customer user) throws NoMatriculationException, ParkingException;
}
