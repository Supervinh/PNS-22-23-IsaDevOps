package interfaces;

import POJO.Customer;
import POJO.PayOff;
import POJO.PayOffPurchase;

public interface PayOffPurchaseRecording {

    boolean recordPayOffPurchase(PayOff transaction, Customer user);

}
