package mfc.components;

import mfc.components.registries.PayoffPurchaseRegistry;
import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.PayoffPurchase;
import mfc.exceptions.*;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
@Transactional
public class PayoffHandler {

    private final CatalogExplorer catalogExplorer;
    private final CustomerBalancesModifier customerBalancesModifier;
    private final ParkingHandler parkingHandler;
    private final PayoffPurchaseRegistry payoffPurchaseRegistry;

    @Autowired
    public PayoffHandler(CatalogExplorer catalogExplorer, CustomerBalancesModifier customerBalancesModifier, ParkingHandler parkingHandler, PayoffPurchaseRegistry payoffPurchaseRegistry) {
        this.catalogExplorer = catalogExplorer;
        this.customerBalancesModifier = customerBalancesModifier;
        this.parkingHandler = parkingHandler;
        this.payoffPurchaseRegistry = payoffPurchaseRegistry;
    }

    public PayoffPurchase claimPayoff(Customer customer, Payoff payoff) throws VFPExpiredException, CustomerNotFoundException, NegativePointCostException, ParkingException, NoMatriculationException {
        if (!catalogExplorer.availablePayoffs(customer).contains(payoff)) {
            throw new VFPExpiredException();
        } else if (payoff.getName().equals("Parking")) {
            parkingHandler.useParkingPayOff(customer);
        }
        customer = customerBalancesModifier.editFidelityPoints(customer, -payoff.getPointCost());
        customer = customerBalancesModifier.editVFP(customer, LocalDate.now().plusDays(2));
        return payoffPurchaseRegistry.recordPayOffPurchase(payoff, customer);
    }
}
