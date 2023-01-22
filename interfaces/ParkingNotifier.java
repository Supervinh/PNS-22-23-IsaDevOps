package interfaces;

import POJO.Customer;

public interface ParkingNotifier {

    boolean notify(Customer target, int remainingTime) ;
}
