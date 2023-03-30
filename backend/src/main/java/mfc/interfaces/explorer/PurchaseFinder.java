package mfc.interfaces.explorer;

import mfc.entities.Customer;
import mfc.entities.Purchase;
import mfc.entities.Store;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PurchaseFinder {
    Set<Purchase> lookUpPurchasesByStore(Store store);

    Set<Purchase> lookUpPayPurchases();

    Set<Purchase> lookUpPurchasesByCustomer(Customer customer);

}

