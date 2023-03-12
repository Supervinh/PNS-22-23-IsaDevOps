package mfc.components;

import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NegativePointCostException;
import mfc.interfaces.TransactionProcessor;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.PurchaseRecording;
import mfc.pojo.Customer;
import mfc.pojo.Purchase;
import mfc.pojo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionHandler implements TransactionProcessor {
    CustomerBalancesModifier customerBalancesModifier;
    PurchaseRecording purchaseRecording;

    @Autowired
    public TransactionHandler(CustomerBalancesModifier customerBalancesModifier, PurchaseRecording purchaseRecording) {
        this.customerBalancesModifier = customerBalancesModifier;
        this.purchaseRecording = purchaseRecording;
    }

    @Override
    public Purchase purchase(Customer customer, double cost, Store store) {
        //gain a point by euro
        try {
            customerBalancesModifier.editFidelityPoints(customer, (int) cost);
            return purchaseRecording.recordPurchase(customer, cost, store);
        } catch (NegativePointCostException | CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Purchase purchaseFidelityCardBalance(Customer user, double cost, Store store) {
        try {
            customerBalancesModifier.editBalance(user, -cost);
            return purchase(user, cost, store);

        } catch (InsufficientBalanceException | CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
