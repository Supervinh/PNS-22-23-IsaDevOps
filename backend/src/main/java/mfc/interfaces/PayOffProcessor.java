package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.POJO.Payoff;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.VFPExpiredException;

public interface PayOffProcessor {
    Customer claimPayOff(Customer user, Payoff payOff) throws InsufficientBalanceException, VFPExpiredException;
}
