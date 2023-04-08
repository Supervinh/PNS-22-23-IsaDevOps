package mfc.components;

import mfc.entities.Customer;
import mfc.entities.Purchase;
import mfc.entities.Store;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NegativePointCostException;
import mfc.interfaces.TransactionProcessor;
import mfc.interfaces.explorer.PurchaseFinder;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.PurchaseRecording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
@Transactional
public class TransactionHandler implements TransactionProcessor {
    private final CustomerBalancesModifier customerBalancesModifier;
    private final PurchaseRecording purchaseRecording;
    private final PurchaseFinder purchaseFinder;

    @Autowired
    public TransactionHandler(CustomerBalancesModifier customerBalancesModifier, PurchaseRecording purchaseRecording, PurchaseFinder purchaseFinder) {
        this.customerBalancesModifier = customerBalancesModifier;
        this.purchaseRecording = purchaseRecording;
        this.purchaseFinder = purchaseFinder;
    }

    @Override
    public Purchase purchase(Customer customer, double cost, Store store) throws NegativePointCostException, CustomerNotFoundException {
        //gain a point by euro
        customerBalancesModifier.editFidelityPoints(customer, (int) cost);
        updateVFP(customer);
        return purchaseRecording.recordPurchase(customer, cost, store);
    }

    @Override
    public Purchase purchaseFidelityCardBalance(Customer customer, double cost, Store store) throws InsufficientBalanceException, CustomerNotFoundException, NegativePointCostException {
        customerBalancesModifier.editBalance(customer, -cost);
        customerBalancesModifier.editFidelityPoints(customer, (int) cost);
        updateVFP(customer);
        return purchaseRecording.recordPurchase(customer, cost, store);
    }

    private void updateVFP(Customer customer) throws CustomerNotFoundException {
        if (purchaseFinder.lookUpPurchasesByCustomer(customer).stream().filter(e -> e.getDate().isAfter(LocalDate.now().minusDays(7))).count() >= 4) {
            customerBalancesModifier.editVFP(customer, LocalDate.now().plusDays(7));
        }
    }
}
