package interfaces;

import POJO.Customer;
import POJO.Purchase;

public interface PurchaseRecording {
    boolean recordPurchase(Purchase purchase, Customer user);

}
