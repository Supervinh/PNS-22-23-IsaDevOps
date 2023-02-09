package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.POJO.Purchase;
import mfc.interfaces.Exceptions.InsufficientBalanceException;

public interface TransactionProcessor {
    //transaction paid without the application, register the transaction and add points
    Purchase purchase(Customer user, double cost);

    //transaction paid with the application, checks balance and register transaction and add points
    Purchase purchaseFidelityCardBalance(Customer user, double cost) throws InsufficientBalanceException;
}
