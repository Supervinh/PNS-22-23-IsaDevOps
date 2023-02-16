package mfc.interfaces.notifier;

import mfc.POJO.Customer;

public interface ParkingNotifier {
    boolean notify(Customer target, int remainingTime);
}
