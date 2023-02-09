package mfc.composants;

import mfc.POJO.Customer;
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
    public Customer purchase(Customer user, double cost) {
        //gain a point by euro
        try {
            customerBalancesModifier.editFidelityPoints(user, (int) cost);
            //TODO: est ce que l'objet purchase doit etre cree ici?
            //purchaseRecording.recordPurchase();
        } catch (NegativePointCostException negativePointCostException){
            System.out.println(negativePointCostException.getMessage());
        }
        return user;
    }

    @Override
    public Customer purchaseFidelityBalance(Customer user, double cost) throws InsufficientBalanceException {

        try {
            customerBalancesModifier.editBalance(user, -cost);
            purchase(user, cost);
        } catch (InsufficientBalanceException insufficientBalanceException){
            System.out.println(insufficientBalanceException.getMessage());
        }
        return user;
    }
}
