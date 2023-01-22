package interfaces;

import POJO.Customer;
import POJO.Purchase;
import POJO.Store;

import java.util.Set;

public interface PurchaseFinder {

    Set<Purchase> lookUpPurchasesByStore(Store store);
    Set<Purchase> lookUpPayPurchases();
    Set<Purchase> lookUpPurchasesByCustomer(Customer customer);
}

