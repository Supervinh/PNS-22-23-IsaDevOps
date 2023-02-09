package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.POJO.PayOff;
import mfc.interfaces.Exceptions.InsufficientBalanceException;
import mfc.interfaces.Exceptions.VFPExpiredException;

public interface PayOffProcessor {
    Customer claimPayOff(Customer user, PayOff payOff) throws InsufficientBalanceException, VFPExpiredException;
}
