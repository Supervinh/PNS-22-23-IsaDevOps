package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.POJO.Purchase;
import mfc.interfaces.Exceptions.AlreadyExistingPurchaseException;

public interface PurchaseRecording {
    Purchase recordPurchase(Customer customer, double cost);
}
