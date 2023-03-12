package mfc.components;

import mfc.exceptions.*;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.pojo.Customer;
import mfc.pojo.Payoff;
import mfc.pojo.PayoffPurchase;
import mfc.repositories.PayoffPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PayoffHandler {

    @Autowired
    CatalogExplorer catalogExplorer;
    @Autowired
    PayoffPurchaseRepository payoffPurchaseRepository;
    @Autowired
    CustomerBalancesModifier customerBalancesModifier;
    @Autowired
    ParkingHandler parkingHandler;

    public PayoffPurchase claimPayoff(Customer customer, Payoff payoff) throws VFPExpiredException, CustomerNotFoundException, NegativePointCostException, ParkingException, NoMatriculationException {
        if (!catalogExplorer.availablePayoffs(customer).contains(payoff)) {
            throw new VFPExpiredException();
        } else if (payoff.getName().equals("Parking")) {
            parkingHandler.useParkingPayOff(customer);
        }
        customer = customerBalancesModifier.editFidelityPoints(customer, -payoff.getPointCost());
        customer = customerBalancesModifier.editVFP(customer, LocalDate.now().plusDays(2));
        PayoffPurchase payoffPurchase = new PayoffPurchase(payoff, customer);
        payoffPurchaseRepository.save(payoffPurchase, payoffPurchase.getId());
        return payoffPurchase;
    }
}
