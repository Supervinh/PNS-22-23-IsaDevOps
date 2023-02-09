package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.POJO.PayOff;

public interface PayOffPurchaseRecording {
    boolean recordPayOffPurchase(PayOff transaction, Customer user);
}
