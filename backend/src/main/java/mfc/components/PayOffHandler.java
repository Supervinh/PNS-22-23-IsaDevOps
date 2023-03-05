package mfc.components;

import mfc.POJO.Customer;
import mfc.POJO.Payoff;
import mfc.POJO.PayoffPurchase;
import mfc.exceptions.VFPExpiredException;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.repositories.PayoffPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PayOffHandler {

    @Autowired
    CatalogExplorer catalogExplorer;
    @Autowired
    PayoffPurchaseRepository payoffPurchaseRepository;

    public PayoffPurchase claimPayoff(Customer customer, Payoff payoff) throws VFPExpiredException {
        if (!catalogExplorer.availablePayoffs(customer).contains(payoff)) {
            throw new VFPExpiredException(); //TODO handle prolongation of VFP
        }
        customer.setFidelityPoints(customer.getFidelityPoints() - payoff.getPointCost());
        customer.setVfp(LocalDate.now());
        PayoffPurchase payoffPurchase = new PayoffPurchase(payoff, customer);
        payoffPurchaseRepository.save(payoffPurchase, payoffPurchase.getId());
        return payoffPurchase;
    }
}
