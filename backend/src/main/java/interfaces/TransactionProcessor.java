package interfaces;

import POJO.Customer;
import interfaces.Exceptions.InsufficientBalanceException;

public interface TransactionProcessor {
    //transaction paid without the application, register the transaction and add points
    Customer purchase(Customer user, double cost);

    //transaction paid with the application, checks balance and register transaction and add points
    Customer purchaseFidelityBalance(Customer user, double cost) throws InsufficientBalanceException;
}
