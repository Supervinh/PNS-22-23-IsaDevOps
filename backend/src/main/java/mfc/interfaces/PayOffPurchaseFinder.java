package mfc.interfaces;

import mfc.POJO.Store;

import java.util.Set;

public interface PayOffPurchaseFinder {
    Set<PayOffPurchaseFinder> lookUpPayOffPurchasesByStore(Store store);

    Set<PayOffPurchaseFinder> lookUpPayOffPurchases();
}
