package interfaces;

import POJO.Customer;
import interfaces.Exceptions.InsufficientBalanceException;

public interface TransactionProcessor {
    Customer purchase(Customer user, double cost);

    Customer purchaseFidelityBalance(Customer user, double cost) throws InsufficientBalanceException;
}
