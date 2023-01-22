package interfaces;

import POJO.Customer;
import POJO.PayOff;
import interfaces.Exceptions.InsufficientBalanceException;
import interfaces.Exceptions.VFPExpiredException;

public interface PayOffProcessor {
    Customer claimPayOff(Customer user, PayOff payOff) throws InsufficientBalanceException, VFPExpiredException;
}
