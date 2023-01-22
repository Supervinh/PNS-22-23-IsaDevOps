package interfaces;

import POJO.Customer;
import interfaces.Exceptions.NoMatriculationException;

public interface ParkingProcessor {

   boolean useParkingPayOff(Customer user) throws NoMatriculationException;

}
