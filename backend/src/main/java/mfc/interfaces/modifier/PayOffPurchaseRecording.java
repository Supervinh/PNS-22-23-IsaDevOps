package mfc.interfaces.modifier;

import mfc.POJO.Customer;
import mfc.POJO.Payoff;

public interface PayOffPurchaseRecording {
    boolean recordPayOffPurchase(Payoff transaction, Customer user);
}
