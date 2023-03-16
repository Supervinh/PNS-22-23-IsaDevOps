package mfc.interfaces.modifier;

import mfc.entities.Customer;
import mfc.entities.Payoff;

public interface PayOffPurchaseRecording {
    boolean recordPayOffPurchase(Payoff transaction, Customer user);
}
