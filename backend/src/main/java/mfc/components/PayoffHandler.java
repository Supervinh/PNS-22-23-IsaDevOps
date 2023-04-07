package mfc.components;

import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.PayoffPurchase;
import mfc.exceptions.*;
import mfc.interfaces.ParkingProcessor;
import mfc.interfaces.PayOffProcessor;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.PayOffPurchaseRecording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class PayoffHandler implements PayOffProcessor {

    private final CatalogExplorer catalogExplorer;
    private final CustomerBalancesModifier customerBalancesModifier;
    private final ParkingProcessor parkingProcessor;
    private final PayOffPurchaseRecording payOffPurchaseRecording;

    @Autowired
    public PayoffHandler(CatalogExplorer catalogExplorer, CustomerBalancesModifier customerBalancesModifier, ParkingProcessor parkingProcessor, PayOffPurchaseRecording payOffPurchaseRecording) {
        this.catalogExplorer = catalogExplorer;
        this.customerBalancesModifier = customerBalancesModifier;
        this.parkingProcessor = parkingProcessor;
        this.payOffPurchaseRecording = payOffPurchaseRecording;
    }

    @Override
    public PayoffPurchase claimPayoff(Customer customer, Payoff payoff) throws InsufficientBalanceException, VFPExpiredException, NoMatriculationException, ParkingException, CustomerNotFoundException, NoPreviousPurchaseException, NegativePointCostException {
        catalogExplorer.isAvailablePayoff(customer, payoff); //check if payoff is available, throws exception if not
        if (payoff.getName().equals("Parking")) {
            parkingProcessor.useParkingPayOff(customer);
        }
        customer = customerBalancesModifier.editFidelityPoints(customer, -payoff.getPointCost());
        return payOffPurchaseRecording.recordPayOffPurchase(payoff, customer);

    }
}
