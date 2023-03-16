package mfc.interfaces.modifier;

import mfc.entities.Customer;
import mfc.entities.Purchase;
import mfc.entities.Store;

public interface PurchaseRecording {
    Purchase recordPurchase(Customer customer, double cost, Store store);
}
