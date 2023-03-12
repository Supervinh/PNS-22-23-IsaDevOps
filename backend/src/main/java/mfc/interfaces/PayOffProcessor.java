package mfc.interfaces;

import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.VFPExpiredException;
import mfc.pojo.Customer;
import mfc.pojo.Payoff;

public interface PayOffProcessor {
    Customer claimPayOff(Customer user, Payoff payOff) throws InsufficientBalanceException, VFPExpiredException;
}
