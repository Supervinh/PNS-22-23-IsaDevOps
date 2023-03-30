package mfc.interfaces;

import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.PayoffPurchase;
import mfc.exceptions.*;

public interface PayOffProcessor {
    PayoffPurchase claimPayOff(Customer user, Payoff payOff) throws InsufficientBalanceException, VFPExpiredException, NoMatriculationException, ParkingException, NegativePointCostException, CustomerNotFoundException, PayoffNotFoundException;
}
