package mfc.interfaces.notifier;

import mfc.entities.Customer;

public interface ParkingNotifier {
    boolean notify(Customer target, int remainingTime);
}
