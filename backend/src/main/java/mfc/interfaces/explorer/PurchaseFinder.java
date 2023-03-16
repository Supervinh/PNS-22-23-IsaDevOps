package mfc.interfaces.explorer;

import mfc.pojo.Customer;
import mfc.pojo.Purchase;
import mfc.pojo.Store;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PurchaseFinder {
    Set<Purchase> lookUpPurchasesByStore(Store store);

    Set<Purchase> lookUpPayPurchases();

    Set<Purchase> lookUpPurchasesByCustomer(Customer customer);

    Optional<Purchase> findById(UUID id);
}

