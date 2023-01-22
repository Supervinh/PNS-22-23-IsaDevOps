package interfaces;

import POJO.Customer;
import POJO.PayOff;
import POJO.PayOffPurchase;
import POJO.Purchase;

public interface PurchaseRecording {

    boolean recordPurchase(Purchase purchase, Customer user);

}
