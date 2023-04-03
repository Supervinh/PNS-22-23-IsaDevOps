package mfc.interfaces;

import mfc.entities.Customer;
import mfc.entities.Purchase;
import mfc.entities.Store;
import mfc.exceptions.InsufficientBalanceException;

public interface TransactionProcessor {
    //transaction paid without the application, register the transaction and add points
    Purchase purchase(Customer user, double cost, Store store);

    //transaction paid with the application, checks balance and register transaction and add points
    Purchase purchaseFidelityCardBalance(Customer user, double cost, Store store) throws InsufficientBalanceException;
}
