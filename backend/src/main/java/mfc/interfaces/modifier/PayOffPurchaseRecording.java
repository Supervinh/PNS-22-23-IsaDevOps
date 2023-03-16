package mfc.interfaces.modifier;

import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.PayoffPurchase;

public interface PayOffPurchaseRecording {
    PayoffPurchase recordPayOffPurchase(Payoff transaction, Customer user);
}
