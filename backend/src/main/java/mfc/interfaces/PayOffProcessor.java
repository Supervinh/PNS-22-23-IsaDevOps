package mfc.interfaces;

import mfc.entities.PayoffPurchase;
import mfc.exceptions.*;
import mfc.entities.Customer;
import mfc.entities.Payoff;

public interface PayOffProcessor {
    PayoffPurchase claimPayoff(Customer customer, Payoff payOff) throws VFPExpiredException, CustomerNotFoundException, NegativePointCostException, ParkingException, NoMatriculationException;
}
