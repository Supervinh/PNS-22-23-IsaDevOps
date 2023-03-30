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
import java.time.LocalDate;

@Component
@Transactional
public class PayoffHandler implements PayOffProcessor {

    private final CatalogExplorer catalogExplorer;
    private final CustomerBalancesModifier customerBalancesModifier;
    private final ParkingProcessor parkingHandler;
    private final PayOffPurchaseRecording payoffPurchaseRegistry;

    @Autowired
    public PayoffHandler(CatalogExplorer catalogExplorer, CustomerBalancesModifier customerBalancesModifier, ParkingProcessor parkingHandler, PayOffPurchaseRecording payoffPurchaseRegistry) {
        this.catalogExplorer = catalogExplorer;
        this.customerBalancesModifier = customerBalancesModifier;
        this.parkingHandler = parkingHandler;
        this.payoffPurchaseRegistry = payoffPurchaseRegistry;
    }

    @Override
    public PayoffPurchase claimPayOff(Customer customer, Payoff payoff) throws InsufficientBalanceException, VFPExpiredException, NoMatriculationException, ParkingException, NegativePointCostException, CustomerNotFoundException, PayoffNotFoundException {
        catalogExplorer.isAvailablePayoff(customer, payoff); //check if payoff is available, throws exception if not
        if (payoff.getName().equals("Parking")) {
            parkingHandler.useParkingPayOff(customer);
        }
        customer = customerBalancesModifier.editFidelityPoints(customer, -payoff.getPointCost());
        customer = customerBalancesModifier.editVFP(customer, LocalDate.now().plusDays(2));
        return payoffPurchaseRegistry.recordPayOffPurchase(payoff, customer);

    }
}
