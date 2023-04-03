package mfc.interfaces.explorer;

import mfc.entities.PayoffPurchase;
import mfc.entities.Store;

import java.util.Set;

public interface PayOffPurchaseFinder {
    Set<PayoffPurchase> lookUpPayOffPurchasesByStore(Store store);

    Set<PayoffPurchase> lookUpPayOffPurchases();
}
