package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.interfaces.Exceptions.NoMatriculationException;
import mfc.interfaces.Exceptions.ParkingException;

public interface ParkingProcessor {
    boolean useParkingPayOff(Customer user) throws NoMatriculationException, ParkingException;
}
