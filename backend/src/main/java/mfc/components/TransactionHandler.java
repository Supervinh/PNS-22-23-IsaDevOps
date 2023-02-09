package mfc.components;

import mfc.POJO.Customer;
import mfc.POJO.Purchase;
import mfc.interfaces.CustomerBalancesModifier;
import mfc.interfaces.Exceptions.InsufficientBalanceException;
import mfc.interfaces.Exceptions.NegativePointCostException;
import mfc.interfaces.PurchaseRecording;
import mfc.interfaces.TransactionProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransactionHandler implements TransactionProcessor {
    CustomerBalancesModifier customerBalancesModifier;
    PurchaseRecording purchaseRecording;

    @Override
    public Purchase purchase(Customer customer, double cost) {
        //gain a point by euro
        try {
            customerBalancesModifier.editFidelityPoints(customer, (int) cost);
            //TODO: est ce que l'objet purchase doit etre cree ici?
            Purchase purchase = purchaseRecording.recordPurchase(customer, cost);
            return purchase;
        } catch (NegativePointCostException negativePointCostException){
            System.out.println(negativePointCostException.getMessage());
        }
        return null;
    }

    @Override
    public Purchase purchaseFidelityCardBalance(Customer user, double cost) throws InsufficientBalanceException {
        try {
            customerBalancesModifier.editBalance(user, -cost);
            return purchase(user, cost);

        } catch (InsufficientBalanceException insufficientBalanceException){
            System.out.println(insufficientBalanceException.getMessage());
        }
        return null;
    }
}
