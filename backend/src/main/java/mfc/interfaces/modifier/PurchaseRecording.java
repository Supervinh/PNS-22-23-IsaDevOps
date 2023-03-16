package mfc.interfaces.modifier;

import mfc.pojo.Customer;
import mfc.pojo.Purchase;
import mfc.pojo.Store;

public interface PurchaseRecording {
    Purchase recordPurchase(Customer customer, double cost, Store store);
}
