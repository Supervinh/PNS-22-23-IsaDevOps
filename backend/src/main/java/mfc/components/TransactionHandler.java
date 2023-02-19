package mfc.components;

import mfc.POJO.Customer;
import mfc.POJO.Purchase;
import mfc.POJO.Store;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NegativePointCostException;
import mfc.interfaces.TransactionProcessor;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.PurchaseRecording;
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
        } catch (NegativePointCostException negativePointCostException) {
            System.out.println(negativePointCostException.getMessage());
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Purchase purchaseFidelityCardBalance(Customer user, double cost, Store store) {
        try {
            customerBalancesModifier.editBalance(user, -cost);
            return purchase(user, cost, store);

        } catch (InsufficientBalanceException | CustomerNotFoundException insufficientBalanceException) {
            System.out.println(insufficientBalanceException.getMessage());
        }
        return null;
    }
}
