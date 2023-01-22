package interfaces;

import POJO.Customer;
import interfaces.Exceptions.NoMatriculationException;
import interfaces.Exceptions.ParkingException;

public interface ParkingProcessor {
    boolean useParkingPayOff(Customer user) throws NoMatriculationException, ParkingException;
}
