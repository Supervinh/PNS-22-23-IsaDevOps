package mfc.interfaces.modifier;

import mfc.POJO.Customer;
import mfc.POJO.Purchase;
import mfc.POJO.Store;

public interface PurchaseRecording {
    Purchase recordPurchase(Customer customer, double cost, Store store);
}
