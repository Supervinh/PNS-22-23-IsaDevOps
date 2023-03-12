package mfc.interfaces;

import mfc.exceptions.InsufficientBalanceException;
import mfc.pojo.Customer;
import mfc.pojo.Purchase;
import mfc.pojo.Store;

public interface TransactionProcessor {
    //transaction paid without the application, register the transaction and add points
    Purchase purchase(Customer user, double cost, Store store);

    //transaction paid with the application, checks balance and register transaction and add points
    Purchase purchaseFidelityCardBalance(Customer user, double cost, Store store) throws InsufficientBalanceException;
}
