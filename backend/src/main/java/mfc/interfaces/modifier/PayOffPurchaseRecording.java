package mfc.interfaces.modifier;

import mfc.pojo.Customer;
import mfc.pojo.Payoff;

public interface PayOffPurchaseRecording {
    boolean recordPayOffPurchase(Payoff transaction, Customer user);
}
