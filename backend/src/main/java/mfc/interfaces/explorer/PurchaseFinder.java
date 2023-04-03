package mfc.interfaces.explorer;

import mfc.entities.Customer;
import mfc.entities.Purchase;
import mfc.entities.Store;

import java.util.Set;

public interface PurchaseFinder {
    Set<Purchase> lookUpPurchasesByStore(Store store);

    Set<Purchase> lookUpPayPurchases();

    Set<Purchase> lookUpPurchasesByCustomer(Customer customer);

}

