package mfc.interfaces;

import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.VFPExpiredException;
import mfc.entities.Customer;
import mfc.entities.Payoff;

public interface PayOffProcessor {
    Customer claimPayOff(Customer user, Payoff payOff) throws InsufficientBalanceException, VFPExpiredException;
}
