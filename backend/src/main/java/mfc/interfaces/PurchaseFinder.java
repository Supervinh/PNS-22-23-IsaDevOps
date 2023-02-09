package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.POJO.Purchase;
import mfc.POJO.Store;

import java.util.Set;

public interface PurchaseFinder {
    Set<Purchase> lookUpPurchasesByStore(Store store);

    Set<Purchase> lookUpPayPurchases();

    Set<Purchase> lookUpPurchasesByCustomer(Customer customer);
}

