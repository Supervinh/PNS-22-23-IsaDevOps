package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.POJO.PayOff;
import mfc.interfaces.exceptions.InsufficientBalanceException;
import mfc.interfaces.exceptions.VFPExpiredException;

public interface PayOffProcessor {
    Customer claimPayOff(Customer user, PayOff payOff) throws InsufficientBalanceException, VFPExpiredException;
}
