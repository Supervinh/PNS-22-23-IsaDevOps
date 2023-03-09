package mfc.components;

import mfc.POJO.Customer;
import mfc.POJO.Payoff;
import mfc.POJO.PayoffPurchase;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.VFPExpiredException;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.repositories.PayoffPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
@Transactional
public class PayoffHandler {

    @Autowired
    CatalogExplorer catalogExplorer;
    @Autowired
    PayoffPurchaseRepository payoffPurchaseRepository;
    @Autowired
    CustomerBalancesModifier customerBalancesModifier;

    public PayoffPurchase claimPayoff(Customer customer, Payoff payoff) throws VFPExpiredException, CustomerNotFoundException, NegativePointCostException {
        if (!catalogExplorer.availablePayoffs(customer).contains(payoff)) {
            throw new VFPExpiredException();//TODO correct errors
        }
        customer = customerBalancesModifier.editFidelityPoints(customer, -payoff.getPointCost());
        customer = customerBalancesModifier.editVFP(customer, LocalDate.now().plusDays(2));
        PayoffPurchase payoffPurchase = new PayoffPurchase(payoff, customer);
        payoffPurchaseRepository.save(payoffPurchase);
        return payoffPurchase;
    }
}
