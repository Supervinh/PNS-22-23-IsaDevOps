package mfc.interfaces.notifier;

import mfc.pojo.Customer;

public interface ParkingNotifier {
    boolean notify(Customer target, int remainingTime);
}
