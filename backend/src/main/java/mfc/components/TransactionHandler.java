package mfc.components;

import mfc.POJO.Customer;
import mfc.POJO.Purchase;
import mfc.POJO.Store;
import mfc.interfaces.exceptions.CustomerNotFoundException;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.exceptions.InsufficientBalanceException;
import mfc.interfaces.exceptions.NegativePointCostException;
import mfc.interfaces.modifier.PurchaseRecording;
import mfc.interfaces.TransactionProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransactionHandler implements TransactionProcessor {
    CustomerBalancesModifier customerBalancesModifier;
    PurchaseRecording purchaseRecording;

    @Override
    public Purchase purchase(Customer customer, double cost, Store store) {
        //gain a point by euro
        try {
            customerBalancesModifier.editFidelityPoints(customer, (int) cost);
            Purchase purchase = purchaseRecording.recordPurchase(customer, cost, store);
            return purchase;
        } catch (NegativePointCostException negativePointCostException){
            System.out.println(negativePointCostException.getMessage());
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Purchase purchaseFidelityCardBalance(Customer user, double cost, Store store) throws InsufficientBalanceException {
        try {
            customerBalancesModifier.editBalance(user, -cost);
            return purchase(user, cost, store);

        } catch (InsufficientBalanceException | CustomerNotFoundException insufficientBalanceException){
            System.out.println(insufficientBalanceException.getMessage());
        }
        return null;
    }
}
