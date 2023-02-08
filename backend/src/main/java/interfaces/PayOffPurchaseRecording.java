package interfaces;

import POJO.Customer;
import POJO.PayOff;

public interface PayOffPurchaseRecording {
    boolean recordPayOffPurchase(PayOff transaction, Customer user);
}
