package mfc.interfaces;

import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.PayoffPurchase;
import mfc.exceptions.*;

public interface PayOffProcessor {
    PayoffPurchase claimPayoff(Customer user, Payoff payOff) throws InsufficientBalanceException, VFPExpiredException, NoMatriculationException, ParkingException, NegativePointCostException, AccountNotFoundException, PayoffNotFoundException, NoPreviousPurchaseException;
}
