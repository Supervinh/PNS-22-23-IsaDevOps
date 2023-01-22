package interfaces;

import POJO.Customer;
import POJO.PayOff;

public interface PayOffProcessor {
    Customer claimPayOff(Customer user, PayOff payOff);
}
